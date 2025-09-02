package com.melo.bets.domain.dto.bet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@JsonIgnoreProperties(ignoreUnknown = true)
public record BetCreateDto(

        @NotBlank(message = "the title is required")
        String title,

        String description,

        @NotNull
        @DecimalMin(value = "1.0", message = "Odds must be greater than 1.0")
        BigDecimal odds,

        @NotNull
        @DecimalMin(value = "0.0", message = "Price must be greater than or equal to zero.")
        BigDecimal price,

        @NotNull
        @Future(message = "The date must be in the future")
        LocalDateTime date,

        String betType,

        @NotNull(message = "the competition id is required")
        UUID competitionId,

        @NotNull(message = "the user id is required")
        UUID userId,

        String image
) {}
