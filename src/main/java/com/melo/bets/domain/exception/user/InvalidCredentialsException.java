package com.melo.bets.domain.exception.user;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("The email or password are incorrect");
    }
}
