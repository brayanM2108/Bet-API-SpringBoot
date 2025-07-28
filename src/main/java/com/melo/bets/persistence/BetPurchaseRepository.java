package com.melo.bets.persistence;

import com.melo.bets.domain.BetPurchase;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import com.melo.bets.persistence.crud.BetPurchaseCrudRepository;
import com.melo.bets.persistence.mapper.BetPurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BetPurchaseRepository implements IBetPurchaseRepository {

    private final BetPurchaseCrudRepository betPurchaseCrudRepository;
    private final BetPurchaseMapper betPurchaseMapper;

    @Autowired
    public BetPurchaseRepository(BetPurchaseCrudRepository betPurchaseCrudRepository, BetPurchaseMapper betPurchaseMapper) {
        this.betPurchaseCrudRepository = betPurchaseCrudRepository;
        this.betPurchaseMapper = betPurchaseMapper;
    }

    @Override
    public List<BetPurchase> findAll() {
        return List.of();
    }

    @Override
    public Optional<BetPurchase> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public BetPurchase save(BetPurchase betPurchase) {
        return null;
    }

    @Override
    public Optional<BetPurchase> update(BetPurchase betPurchase) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {

    }
}
