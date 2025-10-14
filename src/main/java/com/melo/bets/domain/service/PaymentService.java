package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.payment.PaymentCreateDto;
import com.melo.bets.domain.dto.payment.PaymentDto;
import com.melo.bets.domain.exception.custom.PaymentNotFoundException;
import com.melo.bets.domain.exception.custom.UserNotFoundException;
import com.melo.bets.domain.repository.IPaymentRepository;
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
    private final UserService userService;
    private final BalanceService balanceService;

    @Autowired
    public PaymentService(IPaymentRepository paymentRepository, UserService userService, BalanceService balanceService) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.balanceService = balanceService;
    }

    public Page<PaymentDto> getAll(int page, int elements, String sortBy, String SortDirection) {
        Pageable pageRequest = PageRequest.of(page, elements);
        Sort sort = Sort.by(Sort.Direction.fromString(SortDirection), sortBy);
        return paymentRepository.findAll(pageRequest);
    }

    public Optional<PaymentDto> getById(UUID id) {
        if (paymentRepository.findById(id).isEmpty()) {
            throw new PaymentNotFoundException(id);
        }
        return paymentRepository.findById(id);
    }

    public Page<PaymentDto> getByUser(int page, int elements, UUID userId) {
        if (userService.getById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        Pageable pageRequest = PageRequest.of(page, elements);
        return paymentRepository.findByUserId(pageRequest, userId);
    }

    public PaymentCreateDto save(PaymentCreateDto payment) {

        if (userService.getById(payment.userId()).isEmpty()) {
            throw new UserNotFoundException(payment.userId());
        }
        balanceService.addBalance(payment.userId(), payment.amount());

        return paymentRepository.save(payment);
    }

    public boolean delete(UUID id) {
        if (getById(id).isEmpty()) {
            throw new PaymentNotFoundException(id);
        }
        return true;
    }

}
