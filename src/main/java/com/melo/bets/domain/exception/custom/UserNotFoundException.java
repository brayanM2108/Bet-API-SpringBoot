package com.melo.bets.domain.exception.custom;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("The user with id " + id + " does not exist");
    }
}
