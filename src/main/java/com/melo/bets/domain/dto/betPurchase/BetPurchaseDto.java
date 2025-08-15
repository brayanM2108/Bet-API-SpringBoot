package com.melo.bets.domain.dto.betPurchase;

import java.time.LocalDateTime;
import java.util.UUID;

public record BetPurchaseDto (
        UUID id,
        UUID betId,
        UUID userId,
        String betTitle,
        String creatorName,
        String userName,
        LocalDateTime purchaseDate

) {}
