package com.melo.bets.domain.repository;

import com.melo.bets.domain.BetPurchase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetPurchaseRepository {

    List<BetPurchase> findAll();

    Optional<BetPurchase> findById(UUID id);

    BetPurchase save(BetPurchase betPurchase);

    Optional<BetPurchase> update(BetPurchase betPurchase);

    void delete(UUID id);
}
