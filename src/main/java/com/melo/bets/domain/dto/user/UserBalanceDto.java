package com.melo.bets.domain.dto.user;

import java.math.BigDecimal;
import java.util.UUID;

public record UserBalanceDto (
        UUID id,
        BigDecimal balance
) {}
