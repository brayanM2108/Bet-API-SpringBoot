package com.melo.bets.infrastructure.persistence.crud;

import com.melo.bets.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentCrudRepository extends JpaRepository<PaymentEntity, UUID> {

    List<PaymentEntity> findByUserId(UUID userId);
}
