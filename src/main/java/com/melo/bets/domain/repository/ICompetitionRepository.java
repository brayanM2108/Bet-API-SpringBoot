package com.melo.bets.domain.repository;

import com.melo.bets.infrastructure.persistence.entity.CompetitionEntity;

import java.util.Optional;
import java.util.UUID;

public interface ICompetitionRepository {
    Optional<CompetitionEntity> findById(UUID id);
}
