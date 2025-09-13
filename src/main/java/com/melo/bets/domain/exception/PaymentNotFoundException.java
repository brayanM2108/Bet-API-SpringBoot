package com.melo.bets.domain.exception;

import java.util.UUID;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(UUID id) {
        super("the payment with id " + id + " does not exist");
    }
}
