package com.melo.bets.domain.repository;


import com.melo.bets.domain.dto.betPurchase.BetPurchaseCreatorDetailsDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseCreateDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseUserDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBetPurchaseRepository {

    Page<BetPurchaseDto> findAll(Pageable pageable);

    Optional<BetPurchaseDto> findById(UUID id);

    Page<BetPurchaseUserDetailsDto> findByUserId(Pageable pageable, UUID userid);

    Page<BetPurchaseCreatorDetailsDto> findByCreatorId(Pageable pageable, UUID creatorId);

    List<BetPurchaseDto> findByBetId(UUID betId);

    Optional<BetPurchaseDto> findByUserAndBet(UUID userId, UUID betId);

    BetPurchaseCreateDto save(BetPurchaseCreateDto betPurchase);

    void delete(UUID id);








}
