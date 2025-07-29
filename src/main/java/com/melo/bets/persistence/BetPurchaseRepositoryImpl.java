package com.melo.bets.persistence;

import com.melo.bets.domain.BetPurchase;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import com.melo.bets.persistence.crud.BetPurchaseCrudRepository;
import com.melo.bets.persistence.entity.BetPurchaseEntity;
import com.melo.bets.persistence.mapper.BetPurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<BetPurchase> findAll() {
        List<BetPurchaseEntity> betPurchases = betPurchaseCrudRepository.findAll();
        return betPurchaseMapper.toBetPurchaseList(betPurchases);
    }

    @Override
    public Optional<BetPurchase> findById(UUID id) {
        return betPurchaseCrudRepository.findById(id).map(betPurchaseMapper::toBetPurchase);
    }

    @Override
    public List<BetPurchase> findByUserId(UUID id) {
        List<BetPurchaseEntity> betPurchases = betPurchaseCrudRepository.findByUserId(id);
        return betPurchaseMapper.toBetPurchaseList(betPurchases);
    }

    @Override
    public List<BetPurchase> findByBetId(UUID betId) {
        List<BetPurchaseEntity> betPurchases = betPurchaseCrudRepository.findByBetId(betId);
        return betPurchaseMapper.toBetPurchaseList(betPurchases);
    }

    @Override
    public Optional<BetPurchase> getByUserAndBet(UUID userId, UUID betId) {
        return betPurchaseCrudRepository.findByUserIdAndBetId(userId, betId)
                .map(betPurchaseMapper::toBetPurchase);
    }

    @Override
    public BetPurchase save(BetPurchase betPurchase) {
        BetPurchaseEntity betPurchaseEntity = betPurchaseMapper.toBetPurchaseEntity(betPurchase);
        return betPurchaseMapper.toBetPurchase(betPurchaseCrudRepository.save(betPurchaseEntity));
    }

    @Override
    public void delete(UUID id) {
        betPurchaseCrudRepository.deleteById(id);
    }
}
