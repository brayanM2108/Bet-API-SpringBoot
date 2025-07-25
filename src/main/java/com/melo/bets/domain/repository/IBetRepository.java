package com.melo.bets.domain.repository;

import com.melo.bets.domain.Bet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetRepository {

    List<Bet> findAll();

    Optional<Bet> findById(UUID id);

    Bet save(Bet bet);

    Bet update(Bet bet);

    void delete(UUID id);
}
