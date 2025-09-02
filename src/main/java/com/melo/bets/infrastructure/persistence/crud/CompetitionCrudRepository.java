package com.melo.bets.infrastructure.persistence.crud;

import com.melo.bets.infrastructure.persistence.entity.CompetitionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CompetitionCrudRepository extends CrudRepository<CompetitionEntity, UUID> {
}
