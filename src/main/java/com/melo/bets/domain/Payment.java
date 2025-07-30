package com.melo.bets.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private UUID id;

    private BigDecimal amount;

    private String paymentMethod;

    private String paymentType;

    private LocalDateTime paymentDate = LocalDateTime.now();

    private UUID userId;
}
