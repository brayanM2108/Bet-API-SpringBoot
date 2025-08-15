package com.melo.bets.domain.repository;

import com.melo.bets.domain.BetPurchase;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetPurchaseRepository {

    List<BetPurchaseDto> findAll();

    Optional<BetPurchaseDto> findById(UUID id);

    List<BetPurchaseDto> findByUserId(UUID userid);

    List<BetPurchaseDto> findByCreatorId(UUID creatorId);

    List<BetPurchaseDto> findByBetId(UUID betId);

    Optional<BetPurchase> getByUserAndBet(UUID userId, UUID betId);

    BetPurchase save(BetPurchase betPurchase);

    void delete(UUID id);








}
