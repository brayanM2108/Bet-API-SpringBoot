package com.melo.bets.domain.dto.betPurchase;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BetPurchaseCreateDto(

        @NotNull(message = "the betId is required")
        UUID betId
)
{}
