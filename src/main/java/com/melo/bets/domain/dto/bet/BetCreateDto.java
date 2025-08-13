package com.melo.bets.domain.dto.bet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BetCreateDto(
        String title,
        String description,
        BigDecimal odds,
        BigDecimal price,
        LocalDateTime date,
        String betType,
        UUID competitionId,
        UUID userId
) {}
