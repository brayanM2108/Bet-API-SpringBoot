package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.payment.PaymentCreateDto;
import com.melo.bets.domain.dto.payment.PaymentDto;
import com.melo.bets.domain.repository.IPaymentRepository;
import com.melo.bets.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final IPaymentRepository paymentRepository;
    private final IUserRepository userRepository;
    @Autowired
    public PaymentService(IPaymentRepository paymentRepository, IUserRepository userRepository) {
        this.paymentRepository = paymentRepository;

        this.userRepository = userRepository;
    }

    public Page<PaymentDto> getAll(int page, int elements, String sortBy, String SortDirection) {
        Pageable pageRequest = PageRequest.of(page, elements);
        Sort sort = Sort.by(Sort.Direction.fromString(SortDirection), sortBy);
        return paymentRepository.findAll(pageRequest);
    }

    public Optional<PaymentDto> getById(UUID id) {
        return paymentRepository.findById(id);
    }

    public Page<PaymentDto> getByUser(int page, int elements, UUID userId) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return paymentRepository.findByUserId(pageRequest, userId);
    }

    public PaymentCreateDto save(PaymentCreateDto payment) {
        // 1. Verificar que el userId no sea null
        if (payment.userId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        // 2. Buscar el usuario en la base de datos
        userRepository.findById(payment.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + payment.userId()));

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
