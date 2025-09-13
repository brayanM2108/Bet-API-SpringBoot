package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.betPurchase.*;
import com.melo.bets.domain.exception.BetNotFoundException;
import com.melo.bets.domain.exception.UserDoesNotEnoughFundsException;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetPurchaseService {

    private final IBetPurchaseRepository betPurchaseRepository;
    private final UserService userService;
    private final BetService betService;
    private final BalanceService balanceService;

    public BetPurchaseService(IBetPurchaseRepository betPurchaseRepository,
                              UserService userService,
                              BetService betService,
                              BalanceService balanceService) {
        this.betPurchaseRepository = betPurchaseRepository;
        this.userService = userService;
        this.betService = betService;
        this.balanceService = balanceService;
    }

    public Page<BetPurchaseDto> getAll(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return betPurchaseRepository.findAll(pageRequest);
    }

    public Optional<BetPurchaseDto> getById(UUID id) {
        return betPurchaseRepository.findById(id);
    }

    public Page<BetPurchaseUserDetailsDto> getByUser(int pages, int elements, UUID userId) {
        Pageable pageRequest = PageRequest.of(pages, elements);
        return betPurchaseRepository.findByUserId(pageRequest, userId);
    }

    public Page<BetPurchaseCreatorDetailsDto> getByCreatorId(int pages, int elements, UUID creatorId) {
        Pageable pageRequest = PageRequest.of(pages, elements);
        return betPurchaseRepository.findByCreatorId(pageRequest, creatorId);
    }

    public List<BetPurchaseDto> getByBet(UUID betId) {
        return betPurchaseRepository.findByBetId(betId);
    }

    public Optional<BetPurchaseDto> getByUserAndBet(UUID userId, UUID betId) {
        return betPurchaseRepository.findByUserAndBet(userId, betId);
    }

    @Transactional
    public BetPurchaseCreateResponseDto save(BetPurchaseCreateDto betPurchase) {
        UUID userId = betPurchase.userId();
        UUID betId = betPurchase.betId();


        userService.getById(userId);
        betService.get(betId);
        validateUserHasNotPurchasedBet(userId, betId);

        BigDecimal betPrice = betService.getPrice(betId)
                .orElseThrow(() -> new BetNotFoundException(betId))
                .price();

        if (!balanceService.hasSufficientFunds(userId, betPrice)) {
            throw new UserDoesNotEnoughFundsException(userId);
        }

        balanceService.deductBalance(userId, betPrice);
        return betPurchaseRepository.save(betPurchase, betPrice);
    }

    public boolean delete(UUID id) {
        if (getById(id).isPresent()) {
            betPurchaseRepository.delete(id);
            return true;
        }
        return false;
    }

    private void validateUserHasNotPurchasedBet(UUID userId, UUID betId) {
        if (getByUserAndBet(userId, betId).isPresent()) {
            throw new IllegalArgumentException("User has already purchased this bet.");
        }
    }

}
