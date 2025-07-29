package com.melo.bets.domain.service;

import com.melo.bets.domain.BetPurchase;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import com.melo.bets.persistence.crud.BetCrudRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetPurchaseService {

    private final IBetPurchaseRepository betPurchaseRepository;
    private final BetCrudRepository betCrudRepository;
    private final UserCrudRepository userCrudRepository;

    public BetPurchaseService(IBetPurchaseRepository betPurchaseRepository, BetCrudRepository betCrudRepository, UserCrudRepository userCrudRepository) {
        this.betPurchaseRepository = betPurchaseRepository;
        this.betCrudRepository = betCrudRepository;
        this.userCrudRepository = userCrudRepository;
    }

    public List<BetPurchase> getAll() {
        return betPurchaseRepository.findAll();
    }
    public Optional<BetPurchase> getById(UUID id) {
        return betPurchaseRepository.findById(id);
    }

    public List<BetPurchase> getByUser(UUID userId) {
        return betPurchaseRepository.findByUserId(userId);
    }

    public List<BetPurchase> getByBet(UUID betId) {
        return betPurchaseRepository.findByBetId(betId);
    }

    public Optional<BetPurchase> getByUserAndBet(UUID userId, UUID betId) {
        return betPurchaseRepository.getByUserAndBet(userId, betId);
    }

    public BetPurchase save(BetPurchase betPurchase) {
        UUID userId = betPurchase.getUserId();
        UUID betId = betPurchase.getBetId();

        if (userId == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        if (betId == null) {
            throw new IllegalArgumentException("Bet ID is required.");
        }

        // Verifica que el usuario exista
        boolean userExists = userCrudRepository.existsById(userId);
        if (!userExists) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // Verifica que la apuesta exista
        boolean betExists = betCrudRepository.existsById(betId);
        if (!betExists) {
            throw new IllegalArgumentException("Bet not found with ID: " + betId);
        }

        return betPurchaseRepository.save(betPurchase);
    }

    public boolean delete(UUID id) {
        if (getById(id).isPresent()) {
            betPurchaseRepository.delete(id);
            return true;
        }else return false;
    }
}
