package com.melo.bets.domain.exception.custom;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("The email or password are incorrect");
    }
}
