package com.melo.bets.domain.repository;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetRepository {

    List<BetDto> findAll();

    Optional<BetDto> findById(UUID id);

    BetCreateDto save(BetCreateDto bet);

    Optional <BetUpdateDto> update(UUID id, BetUpdateDto bet);

    void delete(UUID id);

    List<BetDto> findByCompetition(UUID competicionId);

    List<BetDto> findByCategory(UUID categoryId);

    List<BetDto> findByCompetitionAndCategory(UUID competitionId, UUID categoryId);

    Page<BetDto> findAllAvailable(Pageable pageable);
}
