package com.melo.bets.domain.dto.betPurchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BetPurchaseUserDetailsDto (
        UUID id,
        UUID betId,
        UUID creatorId,
        LocalDateTime purchaseDate,
        String orderNumber,
        String betTitle,
        String creatorName,
        BigDecimal price
) {
}
