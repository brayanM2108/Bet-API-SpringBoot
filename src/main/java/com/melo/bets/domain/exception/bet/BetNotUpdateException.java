package com.melo.bets.domain.exception.bet;

public class BetNotUpdateException extends RuntimeException {

    public BetNotUpdateException(String message) {
        super("Bet could not be updated" + message);
    }
}
