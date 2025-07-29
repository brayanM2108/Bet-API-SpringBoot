package com.melo.bets.domain.repository;

import com.melo.bets.domain.BetPurchase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetPurchaseRepository {

    List<BetPurchase> findAll();

    Optional<BetPurchase> findById(UUID id);

    List<BetPurchase> findByUserId(UUID userid);

    List<BetPurchase> findByBetId(UUID betId);

    Optional<BetPurchase> getByUserAndBet(UUID userId, UUID betId);

    BetPurchase save(BetPurchase betPurchase);

    void delete(UUID id);








}
