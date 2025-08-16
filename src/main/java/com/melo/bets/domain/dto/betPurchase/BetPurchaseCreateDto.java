package com.melo.bets.domain.dto.betPurchase;

import java.util.UUID;

public record BetPurchaseCreateDto(

        UUID betId,
        UUID userId
)
{}
