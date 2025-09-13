package com.melo.bets.domain.dto.betPurchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BetPurchaseDto (
        UUID id,
        UUID betId,
        UUID userId,
        String orderNumber,
        String betTitle,
        BigDecimal price,
        String creatorName,
        String userName,
        LocalDateTime purchaseDate

) {}
