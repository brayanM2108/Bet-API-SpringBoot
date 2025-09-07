package com.melo.bets.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("The email " + email + " is already registered");
    }
}
