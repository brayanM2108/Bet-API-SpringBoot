package com.melo.bets.infrastructure.persistence.crud;

import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.infrastructure.persistence.entity.BetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BetCrudRepository extends JpaRepository<BetEntity, UUID> {

    @Query(nativeQuery = false, value ="""
SELECT new com.melo.bets.domain.dto.bet.BetDto(
    b.id,
    b.title,
    b.description,
    b.odds,
    b.date,
    b.status,
    b.price,
    b.betType,
    b.result,
    u.name,
    c.name,
    cat.name
)
FROM BetEntity b
JOIN b.creator u
JOIN b.competition c
JOIN c.category cat
""")
    Page<BetDto> findAllProjected(Pageable pageable);

    @Query(nativeQuery = false, value ="""
SELECT new com.melo.bets.domain.dto.bet.BetDto(
    b.id,
    b.title,
    b.description,
    b.odds,
    b.date,
    b.status,
    b.price,
    b.betType,
    b.result,
    u.name,
    c.name,
    cat.name
)
FROM BetEntity b
JOIN b.creator u
JOIN b.competition c
JOIN c.category cat
WHERE c.id = :competitionId
""")
    Page<BetDto> findByCompetitionId(Pageable pageable, @Param("competitionId") UUID competitionId);

    @Query(nativeQuery = false, value ="""
SELECT new com.melo.bets.domain.dto.bet.BetDto(
    b.id,
    b.title,
    b.description,
    b.odds,
    b.date,
    b.status,
    b.price,
    b.betType,
    b.result,
    u.name,
    c.name,
    cat.name
)
FROM BetEntity b
JOIN b.creator u
JOIN b.competition c
JOIN c.category cat
WHERE cat.id = :categoryId
""")
    Page<BetDto> findByCategoryId(@Param("categoryId") UUID categoryId, Pageable pageable);


    @Query(nativeQuery = false, value ="""
SELECT new com.melo.bets.domain.dto.bet.BetDto(
    b.id,
    b.title,
    b.description,
    b.odds,
    b.date,
    b.status,
    b.price,
    b.betType,
    b.result,
    u.name,
    c.name,
    cat.name
)
FROM BetEntity b
JOIN b.creator u
JOIN b.competition c
JOIN c.category cat
WHERE b.status = true
""")
    Page<BetDto> findByStatusTrue(Pageable pageable);

    @Query(value = """
            SELECT new com.melo.bets.domain.dto.bet.BetPriceDto
(u.id, u.price)
FROM BetEntity u WHERE u.id = :betId
""")
    Optional<BetPriceDto> findBetPrice(UUID betId);
}
