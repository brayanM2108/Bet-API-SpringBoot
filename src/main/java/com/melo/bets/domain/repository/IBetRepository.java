package com.melo.bets.domain.repository;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetRepository {

    Page<BetDto> findAll(Pageable pageable);

    Optional<BetDto> findById(UUID id);

    Optional <BetPriceDto> findPrice(UUID id);

    BetCreateDto save(BetCreateDto bet);

    Optional <BetUpdateDto> update(UUID id, BetUpdateDto bet);

    void delete(UUID id);

    Page<BetDto> findByCompetition(Pageable pageable, UUID competicionId);

    Page<BetDto> findByCategory(Pageable pageable, UUID categoryId);

    Page<BetDto> findAllAvailable(Pageable pageable);
}
