package com.melo.bets.domain;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Raffle {

    private UUID id;

    private String title;

    private String description;

    private BigDecimal ticketPrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

    private User creator;

    private List<RaffleParticipation> participations;
}
