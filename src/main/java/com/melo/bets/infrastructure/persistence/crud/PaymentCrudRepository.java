package com.melo.bets.infrastructure.persistence.crud;

import com.melo.bets.domain.dto.payment.PaymentDto;
import com.melo.bets.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PaymentCrudRepository extends JpaRepository<PaymentEntity, UUID> {

    @Query(nativeQuery = false, value ="""
SELECT new com.melo.bets.domain.dto.payment.PaymentDto(
   p.id,
   p.amount,
   p.paymentMethod,
   p.paymentType,
   p.paymentDate,
    u.id,
   u.name
)
FROM PaymentEntity p
JOIN p.userEntity u

""")
    Page<PaymentDto> findAllProjected(Pageable pageable);

    @Query(nativeQuery = false, value ="""
SELECT new com.melo.bets.domain.dto.payment.PaymentDto(
   p.id,
   p.amount,
   p.paymentMethod,
   p.paymentType,
   p.paymentDate,
    u.id,
   u.name
)
FROM PaymentEntity p
JOIN p.userEntity u
WHERE u.id = :userId

""")
    Page<PaymentDto> findByUserId(Pageable pageable, UUID userId);
}
