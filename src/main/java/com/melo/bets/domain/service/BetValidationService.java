package com.melo.bets.domain.service;

import com.melo.bets.domain.repository.IBetPurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BetValidationService {

    private final IBetPurchaseRepository betPurchaseRepository;

    public BetValidationService(IBetPurchaseRepository betPurchaseRepository) {
        this.betPurchaseRepository = betPurchaseRepository;
    }

    public boolean hasExistingPurchases(UUID betId) {
        return !betPurchaseRepository.findByBetId(betId).isEmpty();
    }
}
