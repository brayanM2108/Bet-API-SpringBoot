package com.melo.bets.infrastructure.persistence;

import com.melo.bets.domain.Payment;
import com.melo.bets.domain.repository.IPaymentRepository;
import com.melo.bets.infrastructure.persistence.crud.PaymentCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.PaymentEntity;
import com.melo.bets.infrastructure.persistence.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements IPaymentRepository {

    private final PaymentCrudRepository paymentCrudRepository;
    private final PaymentMapper paymentMapper;
    @Autowired
    public PaymentRepositoryImpl(PaymentCrudRepository paymentCrudRepository, PaymentMapper paymentMapper) {
        this.paymentCrudRepository = paymentCrudRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<Payment> findAll() {
        List<PaymentEntity> payments = paymentCrudRepository.findAll();
        return paymentMapper.toPaymentList(payments);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return paymentCrudRepository.findById(id).map(paymentMapper::toPayment);
    }

    @Override
    public List<Payment> findByUserId(UUID userId) {
        List<PaymentEntity> payments = paymentCrudRepository.findByUserId(userId);
        return paymentMapper.toPaymentList(payments);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = paymentMapper.toPaymentEntity(payment);
        return paymentMapper.toPayment(paymentCrudRepository.save(paymentEntity));
    }

    @Override
    public void delete(UUID id) {
        paymentCrudRepository.deleteById(id);
    }
}
