package com.melo.bets.infrastructure.persistence;


import com.melo.bets.domain.dto.payment.PaymentCreateDto;
import com.melo.bets.domain.dto.payment.PaymentDto;
import com.melo.bets.domain.exception.PaymentNotFoundException;
import com.melo.bets.domain.repository.IPaymentRepository;
import com.melo.bets.infrastructure.persistence.crud.PaymentCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.PaymentEntity;
import com.melo.bets.infrastructure.persistence.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
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
    public Page<PaymentDto> findAll(Pageable pageable) {
        return paymentCrudRepository.findAllProjected(pageable);
    }

    @Override
    public Optional<PaymentDto> findById(UUID id) {
        if (paymentCrudRepository.findById(id).isEmpty()) {
            throw new PaymentNotFoundException(id);
        }
        return paymentCrudRepository.findById(id).map(paymentMapper::toPayment);
    }

    @Override
    public Page<PaymentDto> findByUserId(Pageable pageable, UUID userId) {
        return paymentCrudRepository.findByUserId(pageable, userId);
    }

    @Override
    public PaymentCreateDto save(PaymentCreateDto payment) {
        PaymentEntity paymentEntity = paymentMapper.toPaymentCreateEntity(payment);
        paymentEntity.setPaymentType("recarga");
        return paymentMapper.toPaymentCreateDto(paymentCrudRepository.save(paymentEntity));
    }

    @Override
    public void delete(UUID id) {
        paymentCrudRepository.deleteById(id);
    }
}
