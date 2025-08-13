package com.melo.bets.domain.dto.bet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BetUpdateDto (
        String title,
        String description,
        BigDecimal odds,
        LocalDateTime date,
        Boolean status,
        BigDecimal price,
        String result,
        String betType
        )
{ }
