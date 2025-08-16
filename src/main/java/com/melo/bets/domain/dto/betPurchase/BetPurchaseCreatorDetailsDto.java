package com.melo.bets.domain.dto.betPurchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BetPurchaseCreatorDetailsDto(
        UUID id,
        UUID betId,
        UUID userId,
        LocalDateTime purchaseDate,
        String orderNumber,
        String betTitle,
        String userName,
        BigDecimal price
) {
}
