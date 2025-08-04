package com.melo.bets.domain.service;


import com.melo.bets.domain.Payment;
import com.melo.bets.domain.repository.IPaymentRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final IPaymentRepository paymentRepository;
    private final UserCrudRepository userCrudRepository;
    @Autowired
    public PaymentService(IPaymentRepository paymentRepository, UserCrudRepository userCrudRepository) {
        this.paymentRepository = paymentRepository;
        this.userCrudRepository = userCrudRepository;
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getById(UUID id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getByUser(UUID userId) {
        return paymentRepository.findByUserId(userId);
    }

    public Payment save(Payment payment) {
        // 1. Verificar que el userId no sea null
        if (payment.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        // 2. Buscar el usuario en la base de datos
        userCrudRepository.findById(payment.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + payment.getUserId()));

        return paymentRepository.save(payment);
    }

    public boolean delete(UUID id) {
        if (getById(id).isPresent()) {
            paymentRepository.delete(id);
            return true;
        }
        return false;
    }
}
