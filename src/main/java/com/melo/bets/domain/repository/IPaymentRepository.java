package com.melo.bets.domain.repository;

import com.melo.bets.domain.dto.payment.PaymentCreateDto;
import com.melo.bets.domain.dto.payment.PaymentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepository {

    Page<PaymentDto> findAll(Pageable pageable);

    Optional<PaymentDto> findById(UUID id);

    Page<PaymentDto> findByUserId(Pageable pageable, UUID userId);

    PaymentCreateDto save(PaymentCreateDto payment);

    void delete(UUID id);
}
