package com.melo.bets.infrastructure.persistence;

import com.melo.bets.domain.dto.betPurchase.BetPurchaseCreatorDetailsDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseCreateDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseUserDetailsDto;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import com.melo.bets.infrastructure.persistence.crud.BetPurchaseCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.BetPurchaseEntity;
import com.melo.bets.infrastructure.persistence.mapper.BetPurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BetPurchaseRepositoryImpl implements IBetPurchaseRepository {

    private final BetPurchaseCrudRepository betPurchaseCrudRepository;
    private final BetPurchaseMapper betPurchaseMapper;

    @Autowired
    public BetPurchaseRepositoryImpl(BetPurchaseCrudRepository betPurchaseCrudRepository, BetPurchaseMapper betPurchaseMapper) {
        this.betPurchaseCrudRepository = betPurchaseCrudRepository;
        this.betPurchaseMapper = betPurchaseMapper;
    }

    @Override
    public Page<BetPurchaseDto> findAll(Pageable pageable) {
        return betPurchaseCrudRepository.findAllProjected(pageable);
    }

    @Override
    public Optional<BetPurchaseDto> findById(UUID id) {
        return betPurchaseCrudRepository.findById(id).map(betPurchaseMapper::toBetPurchaseDto);
    }

    @Override
    public Page<BetPurchaseUserDetailsDto> findByUserId(Pageable pageable, UUID id) {
        return betPurchaseCrudRepository.findByUserId(pageable,id);
    }

    @Override
    public Page<BetPurchaseCreatorDetailsDto> findByCreatorId(Pageable pageable, UUID creatorId) {
        return betPurchaseCrudRepository.findByBetCreatorId(pageable, creatorId);
    }

    @Override
    public List<BetPurchaseDto> findByBetId(UUID betId) {
        List<BetPurchaseEntity> betPurchases = betPurchaseCrudRepository.findByBetId(betId);
        return betPurchaseMapper.toBetPurchaseListDto(betPurchases);
    }

    @Override
    public Optional<BetPurchaseDto> getByUserAndBet(UUID userId, UUID betId) {
        return betPurchaseCrudRepository.findByUserIdAndBetId(userId, betId)
                .map(betPurchaseMapper::toBetPurchaseDto);
    }

    @Override
    public BetPurchaseCreateDto save(BetPurchaseCreateDto betPurchase) {
        BetPurchaseEntity betPurchaseEntity = betPurchaseMapper.toBetPurchaseCreateEntity(betPurchase);

        betPurchaseEntity.setUserId(betPurchase.userId());
        betPurchaseEntity.setBetId(betPurchase.betId());
        betPurchaseEntity.setOrderNumber(generarNumeroOrden()); // Asignas el número de orden aquí
        betPurchaseEntity.setPurchaseDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return betPurchaseMapper.toBetPurchaseCreateDto(betPurchaseCrudRepository.save(betPurchaseEntity));
    }

    @Override
    public void delete(UUID id) {
        betPurchaseCrudRepository.deleteById(id);
    }

    private String generarNumeroOrden() {
        return UUID.randomUUID().toString();
    }
}
