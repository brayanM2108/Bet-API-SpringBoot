package com.melo.bets.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RaffleParticipation {

    private UUID id;

    private Raffle raffle;

    private User user;

    private LocalDateTime participationDate = LocalDateTime.now();
}
