package com.melo.bets.domain.dto.bet;

import java.math.BigDecimal;
import java.util.UUID;

public record BetPriceDto(
        UUID id,
        BigDecimal price
) {
}
