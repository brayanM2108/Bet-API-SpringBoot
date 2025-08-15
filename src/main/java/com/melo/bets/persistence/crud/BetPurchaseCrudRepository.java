package com.melo.bets.persistence.crud;

import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetPurchaseCrudRepository extends JpaRepository<BetPurchaseEntity, UUID> {
    @Query(nativeQuery = false ,value = "SELECT bp FROM BetPurchaseEntity bp" +
            " JOIN FETCH bp.betEntity b JOIN FETCH bp.userEntity u WHERE b.creator.id = :creatorId")
    List<BetPurchaseEntity> findByBetCreatorId(UUID creatorId);

    List<BetPurchaseEntity> findByUserId(UUID userId);

    List<BetPurchaseEntity> findByBetId(UUID betId);

    Optional<BetPurchaseEntity> findByUserIdAndBetId(UUID userId, UUID betId);
}
