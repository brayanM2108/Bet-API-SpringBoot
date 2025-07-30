package com.melo.bets.domain.repository;

import com.melo.bets.domain.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepository {

    List<Payment> findAll();

    Optional<Payment> findById(UUID id);

    List<Payment> findByUserId(UUID userId);

    Payment save(Payment payment);

    void delete(UUID id);
}
