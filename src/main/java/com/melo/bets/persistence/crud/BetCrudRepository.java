package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BetCrudRepository extends JpaRepository<BetEntity, UUID> {

    List<BetEntity> findByCompetitionId(UUID competitionId);
    List<BetEntity> findByCompetitionCategoryId(UUID competitionId);

    List<BetEntity> findByCompetitionIdAndCompetitionCategoryId(UUID competitionId, UUID categoryId);
}
