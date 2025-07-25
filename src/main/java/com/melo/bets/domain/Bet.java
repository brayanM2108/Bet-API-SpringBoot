package com.melo.bets.domain;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Bet {

    private UUID id;

    private String title;

    private String description;

    private BigDecimal odds;

    private String event;

    private LocalDateTime date;

    private String status;

    private BigDecimal price;

    private UUID userId;
}
