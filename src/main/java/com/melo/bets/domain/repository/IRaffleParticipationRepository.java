package com.melo.bets.domain.repository;


import com.melo.bets.domain.RaffleParticipation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRaffleParticipationRepository {

    List<RaffleParticipation> findAll();

    Optional<RaffleParticipation> findById(UUID id);

    List<RaffleParticipation> findByUserId(UUID userid);

    List<RaffleParticipation> findByRaffleId(UUID betId);

    Optional<RaffleParticipation> getByUserAndRaffle(UUID userId, UUID raffleId);

    RaffleParticipation save(RaffleParticipation raffleParticipation);

    void delete(UUID id);
}
