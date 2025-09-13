package com.melo.bets.domain.dto.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreateDto (

        @NotNull(message = "the amount is required")
        @Min(value = 10000, message = "The amount must be greater than 10000")
        BigDecimal amount,

        @NotBlank(message = "the payment method is required")
        String paymentMethod,

        @NotNull(message = "the user id is required")
        UUID userId

) {}
