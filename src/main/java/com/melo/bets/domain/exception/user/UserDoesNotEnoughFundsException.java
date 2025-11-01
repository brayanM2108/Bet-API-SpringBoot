package com.melo.bets.domain.exception.user;

import java.util.UUID;

public class UserDoesNotEnoughFundsException extends RuntimeException {
    public UserDoesNotEnoughFundsException(UUID id) {
        super("the user with id " + id + " does not have enough funds");
    }
}
