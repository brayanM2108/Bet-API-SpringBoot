package com.melo.bets.domain.dto.payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreateDto (
        UUID id,

        BigDecimal amount,

        String paymentMethod,

        String paymentType,

        UUID userId

) {}
