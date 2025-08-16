package com.melo.bets.persistence.crud;

import com.melo.bets.domain.dto.betPurchase.BetPurchaseCreatorDetailsDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseDto;
import com.melo.bets.domain.dto.betPurchase.BetPurchaseUserDetailsDto;
import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BetPurchaseCrudRepository extends JpaRepository<BetPurchaseEntity, UUID> {
    @Query(nativeQuery = false ,value =
            """
 SELECT new com.melo.bets.domain.dto.betPurchase.BetPurchaseDto(
        b.id,
        b.betId,
        b.userEntity.id,
        b.orderNumber,
        bet.title,
        b.betEntity.creator.name,
        b.userEntity.name,
        b.purchaseDate
 )
 FROM BetPurchaseEntity b
 JOIN b.betEntity bet
 JOIN b.userEntity user
 """)
    Page<BetPurchaseDto> findAllProjected(Pageable pageable);

    @Query(nativeQuery = false ,value =
            """
 SELECT new com.melo.bets.domain.dto.betPurchase.BetPurchaseCreatorDetailsDto(
         b.id,
         b.betId,
         b.betEntity.creator.id,
         b.purchaseDate,
         b.orderNumber,
         bet.title,
         b.userEntity.name,
         bet.price
 )
 FROM BetPurchaseEntity b
 JOIN b.betEntity bet
 JOIN b.userEntity user
 WHERE bet.creator.id = :creatorId
 """)
    Page<BetPurchaseCreatorDetailsDto>findByBetCreatorId(Pageable pageable, @Param("creatorId") UUID creatorId);

    @Query(nativeQuery = false ,value =
            """
 SELECT new com.melo.bets.domain.dto.betPurchase.BetPurchaseUserDetailsDto(
         b.id,
         b.betId,
         b.userId,
         b.purchaseDate,
         b.orderNumber,
         bet.title,
         b.betEntity.creator.name,
         bet.price
 )
 FROM BetPurchaseEntity b
 JOIN b.betEntity bet
 JOIN b.userEntity user
 WHERE user.id = :userId
 """)
    Page<BetPurchaseUserDetailsDto> findByUserId(Pageable pageable, @Param("userId") UUID userId);

    List<BetPurchaseEntity> findByBetId(UUID betId);

    Optional<BetPurchaseEntity> findByUserIdAndBetId(UUID userId, UUID betId);
}
