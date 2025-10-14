package com.melo.bets.domain.exception.custom;

public class BetNotUpdateException extends RuntimeException {

    public BetNotUpdateException(String message) {
        super("Bet could not be updated" + message);
    }
}
