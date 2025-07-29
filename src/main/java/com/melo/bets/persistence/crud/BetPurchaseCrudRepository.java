package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetPurchaseCrudRepository extends JpaRepository<BetPurchaseEntity, UUID> {

    List<BetPurchaseEntity> findByUserId(UUID userId);

    List<BetPurchaseEntity> findByBetId(UUID betId);

    Optional<BetPurchaseEntity> findByUserIdAndBetId(UUID userId, UUID betId);
}
