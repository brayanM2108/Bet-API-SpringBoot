package com.melo.bets.domain.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentDto (
         UUID id,

         BigDecimal amount,

         String paymentMethod,

         String paymentType,

         LocalDateTime paymentDate,

         UUID userId,

         String userName
)
{}
