package com.melo.bets.domain.exception.bet;

import java.util.UUID;

public class BetAlreadyPurchasedException extends RuntimeException {
    public BetAlreadyPurchasedException(UUID userId, UUID betId) {
        super(String.format("Usuario %s ya ha comprado la apuesta %s", userId, betId));
    }
}
