package com.melo.bets.domain.repository;

import com.melo.bets.domain.Raffle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRaffleRepository {

    List<Raffle> findAll();

    Optional<Raffle> findById(UUID id);

    Raffle save(Raffle raffle);

    Optional <Raffle> update(Raffle raffle);

    void delete(UUID id);
}
