package com.melo.bets.domain.dto.bet;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;


public record BetUpdateDto (
        
        String title,

        String description,

        @DecimalMin(value = "1.0", message = "Odds must be greater than 1.0")
        BigDecimal odds,

        Boolean status,

        @DecimalMin(value = "0.0", message = "Price must be greater than or equal to zero.")
        BigDecimal price,

        String result,

        String betType
        )
{ }
