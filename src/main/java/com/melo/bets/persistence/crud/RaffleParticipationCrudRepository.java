package com.melo.bets.persistence.crud;

import com.melo.bets.domain.RaffleParticipation;
import com.melo.bets.persistence.entity.RaffleParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RaffleParticipationCrudRepository extends JpaRepository<RaffleParticipationEntity, UUID> {

    List<RaffleParticipationEntity> findByUserId(UUID userId);

    List<RaffleParticipationEntity> findByRaffleId(UUID raffleId);

    Optional<RaffleParticipationEntity> findByUserIdAndRaffleId(UUID userId, UUID raffleId);

}
