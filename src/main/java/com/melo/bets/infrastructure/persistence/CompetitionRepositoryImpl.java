package com.melo.bets.infrastructure.persistence;

import com.melo.bets.domain.exception.custom.CompetitionNotExistException;
import com.melo.bets.domain.repository.ICompetitionRepository;
import com.melo.bets.infrastructure.persistence.crud.CompetitionCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.CompetitionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CompetitionRepositoryImpl implements ICompetitionRepository {

    private final CompetitionCrudRepository competitionCrudRepository;

    public CompetitionRepositoryImpl(CompetitionCrudRepository competitionCrudRepository) {
        this.competitionCrudRepository = competitionCrudRepository;
    }

    @Override
    public Optional<CompetitionEntity> findById(UUID id) {
        if (competitionCrudRepository.findById(id).isEmpty()) throw new CompetitionNotExistException(id);
        return competitionCrudRepository.findById(id);
    }
}
