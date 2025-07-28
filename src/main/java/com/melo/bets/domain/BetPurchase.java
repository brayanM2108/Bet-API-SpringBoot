package com.melo.bets.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class BetPurchase {

    private UUID id;

    private LocalDateTime purchaseDate = LocalDateTime.now();

    private UUID betId;

    private UUID userId;
}
