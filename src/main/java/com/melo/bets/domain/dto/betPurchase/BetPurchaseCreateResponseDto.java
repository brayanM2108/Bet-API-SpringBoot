package com.melo.bets.domain.dto.betPurchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BetPurchaseCreateResponseDto(
        UUID id,
        String orderNumber,
        LocalDateTime purchaseDate,
        UUID userId,
        UUID betId,
        BigDecimal price
) {}