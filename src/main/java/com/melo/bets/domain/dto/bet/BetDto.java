package com.melo.bets.domain.dto.bet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BetDto (
    UUID id,
    String title,
    String description,
    BigDecimal odds,
    LocalDateTime date,
    Boolean status,
    BigDecimal price,
    String betType,
    String result,
    String nameCreator,
    String nameCompetition,
    String nameCategory
) {}
