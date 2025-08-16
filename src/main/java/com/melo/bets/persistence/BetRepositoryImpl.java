package com.melo.bets.persistence;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.repository.IBetRepository;
import com.melo.bets.persistence.crud.BetCrudRepository;
import com.melo.bets.persistence.entity.BetEntity;
import com.melo.bets.persistence.mapper.BetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BetRepositoryImpl implements IBetRepository {
    private final BetCrudRepository betCrudRepository;
    private final BetMapper betMapper;

    @Autowired
    public BetRepositoryImpl(BetCrudRepository betCrudRepository, BetMapper betMapper) {
        this.betCrudRepository = betCrudRepository;
        this.betMapper = betMapper;
    }

    @Override
    public Page<BetDto> findAll(Pageable pageable) {
        return betCrudRepository.findAllProjected(pageable);
    }

    @Override
    public Optional<BetDto> findById(UUID id) {
        return betCrudRepository.findById(id).map(betMapper::toBetDto);
    }

    @Override
    public Optional<BetPriceDto> findPrice(UUID id) {
        return betCrudRepository.findBetPrice(id);
    }

    @Override
    public BetCreateDto save(BetCreateDto bet) {
        BetEntity betEntity = betMapper.toBetCreateEntity(bet);
        return betMapper.toBetCreateDto(betCrudRepository.save(betEntity));
    }

    @Override
    public Optional<BetUpdateDto> update(UUID id, BetUpdateDto bet) {
        Optional<BetEntity> existing = betCrudRepository.findById(id);
        if (existing.isPresent()) {
            BetEntity betEntity = existing.get();

            // Actualizar solo campos no nulos
            if (bet.title() != null) betEntity.setTitle(bet.title());
            if (bet.description() != null) betEntity.setDescription(bet.description());
            if (bet.odds() != null) betEntity.setOdds(bet.odds());
            if (bet.date() != null) betEntity.setDate(bet.date());
            if (bet.status() != null) betEntity.setStatus(bet.status());
            if (bet.price() != null) betEntity.setPrice(bet.price());
            if (bet.result() != null) betEntity.setResult(bet.result());
            if (bet.betType() != null) betEntity.setBetType(bet.betType());

            return Optional.of(betMapper.toBetUpdateDto(betCrudRepository.save(betEntity)));
        }
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {
        betCrudRepository.deleteById(id);
    }

    @Override
    public Page<BetDto> findByCompetition(Pageable pageable ,UUID competicionId) {
        return betCrudRepository.findByCompetitionId(pageable, competicionId);
    }

    @Override
    public Page<BetDto> findByCategory(Pageable pageable, UUID categoryId) {
        return betCrudRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<BetDto> findAllAvailable(Pageable pageable) {
        return betCrudRepository.findByStatusTrue(pageable);
    }
}
