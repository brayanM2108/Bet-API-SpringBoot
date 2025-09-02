package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.payment.PaymentCreateDto;
import com.melo.bets.domain.dto.payment.PaymentDto;
import com.melo.bets.domain.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity <Page<PaymentDto>> getAll(@RequestParam (defaultValue = "0") int page,
                                                    @RequestParam (defaultValue = "10")int elements,
                                                    @RequestParam (defaultValue = "paymentType") String sortBy,
                                                    @RequestParam (defaultValue = "ASC") String sortDirection) {
        return new ResponseEntity<>(paymentService.getAll(page, elements, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable UUID id) {
        return ResponseEntity.of(paymentService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity <Page<PaymentDto>> getByUserId(@RequestParam (defaultValue = "0") int page,
                                                         @RequestParam (defaultValue = "10")int elements,
                                                         @PathVariable UUID userId) {
        return new ResponseEntity<>(paymentService.getByUser(page, elements, userId), HttpStatus.OK)  ;
    }

    @PostMapping
    public ResponseEntity<PaymentCreateDto> save(@RequestBody PaymentCreateDto payment) {
        return new ResponseEntity<>(paymentService.save(payment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return new ResponseEntity<> (paymentService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }
}
