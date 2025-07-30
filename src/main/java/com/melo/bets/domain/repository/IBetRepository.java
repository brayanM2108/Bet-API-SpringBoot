package com.melo.bets.domain.repository;

import com.melo.bets.domain.Bet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetRepository {

    List<Bet> findAll();

    Optional<Bet> findById(UUID id);

    Bet save(Bet bet);

    Optional <Bet> update(Bet bet);

    void delete(UUID id);

    List<Bet> findByCompetition(UUID competicionId);

    List<Bet> findByCategory(UUID categoryId);

    List<Bet> findByCompetitionAndCategory(UUID competitionId, UUID categoryId);
}
