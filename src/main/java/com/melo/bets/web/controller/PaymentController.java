package com.melo.bets.web.controller;

import com.melo.bets.domain.Payment;
import com.melo.bets.domain.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable UUID id) {
        return ResponseEntity.of(paymentService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getByUserId(@PathVariable UUID userId) {
        return paymentService.getByUser(userId);
    }

    @PostMapping
    public ResponseEntity<Payment> save(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.save(payment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return new ResponseEntity<> (paymentService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }
}
