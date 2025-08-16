package com.melo.bets.domain.service;


import com.melo.bets.domain.dto.betPurchase.BetPurchaseCreateDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseCreatorDetailsDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseUserDetailsDto;
import com.melo.bets.domain.dto.user.UserBalanceDto;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import com.melo.bets.persistence.crud.BetCrudRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetPurchaseService {

    private final IBetPurchaseRepository betPurchaseRepository;
    private final BetCrudRepository betCrudRepository;
    private final UserCrudRepository userCrudRepository;
    private final UserService userService;
    private final BetService betService;

    public BetPurchaseService(IBetPurchaseRepository betPurchaseRepository, BetCrudRepository betCrudRepository, UserCrudRepository userCrudRepository, UserService userService, BetService betService) {
        this.betPurchaseRepository = betPurchaseRepository;
        this.betCrudRepository = betCrudRepository;
        this.userCrudRepository = userCrudRepository;
        this.userService = userService;
        this.betService = betService;
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

    public Page<BetPurchaseCreatorDetailsDto> getByCreatorId(int pages, int elements, UUID creatorId){
        Pageable pageRequest = PageRequest.of(pages, elements);
        return betPurchaseRepository.findByCreatorId(pageRequest, creatorId);
    }

    public List<BetPurchaseDto> getByBet(UUID betId) {
        return betPurchaseRepository.findByBetId(betId);
    }

    public Optional<BetPurchaseDto> getByUserAndBet(UUID userId, UUID betId) {
        return betPurchaseRepository.getByUserAndBet(userId, betId);
    }

    @Transactional
    public BetPurchaseCreateDto save(BetPurchaseCreateDto betPurchase) {


        UUID userId = betPurchase.userId();
        UUID betId = betPurchase.betId();


        if (userId == null || betId == null) {
            throw new IllegalArgumentException("User ID and Bet ID are required.");
        }
        // Verifica que el usuario exista
        boolean userExists = userCrudRepository.existsById(userId);
        if (!userExists) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        if (getByUserAndBet(userId, betId).isPresent()) {
            throw new IllegalArgumentException("User has already purchased this bet.");
        }

        // Verifica que la apuesta exista
        boolean betExists = betCrudRepository.existsById(betId);
        if (!betExists) {
            throw new IllegalArgumentException("Bet not found with ID: " + betId);
        }

        BigDecimal balance = userService.getBalance(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario not found with ID: " + userId))
                .balance();

        BigDecimal price = betService.getPrice(betId)
                .orElseThrow(() -> new IllegalArgumentException("Bet not found with ID: " + betId))
                .price();

        if (balance.compareTo(price) < 0) {
            throw new IllegalArgumentException("The user does not have enough funds to make the bet.");
        }

        BigDecimal newBalance = balance.subtract(price);
        userService.updateBalance(new UserBalanceDto(userId, newBalance));

        return betPurchaseRepository.save(betPurchase);
    }

    public boolean delete(UUID id) {
        if (getById(id).isPresent()) {
            betPurchaseRepository.delete(id);
            return true;
        }else return false;
    }

}
