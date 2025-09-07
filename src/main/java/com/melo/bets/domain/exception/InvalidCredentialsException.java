package com.melo.bets.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("The email or password are incorrect");
    }
}
