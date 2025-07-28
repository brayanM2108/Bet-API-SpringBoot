package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BetPurchaseCrudRepository extends JpaRepository<BetPurchaseEntity, UUID> {
}
