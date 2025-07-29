package com.melo.bets.controller;

import com.melo.bets.domain.BetPurchase;
import com.melo.bets.domain.service.BetPurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bet-purchases")
public class BetPurchaseController {

    private final BetPurchaseService betPurchaseService;


    public BetPurchaseController(BetPurchaseService betPurchaseService) {
        this.betPurchaseService = betPurchaseService;
    }

    @GetMapping
    public ResponseEntity <List<BetPurchase>> getAll() {
        return new ResponseEntity<> (betPurchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/bet/{id}")
    public ResponseEntity <List<BetPurchase>> getByBet(@PathVariable ("id") UUID betId) {
        return new ResponseEntity<> (betPurchaseService.getByBet(betId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity <List<BetPurchase>> getByUser(@PathVariable ("id") UUID userId) {
        return new ResponseEntity<> (betPurchaseService.getByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/userandbet")
    public ResponseEntity<BetPurchase> getByUserAndBet(@RequestParam UUID userId,
                                                       @RequestParam UUID betId) {
        return betPurchaseService.getByUserAndBet(userId, betId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BetPurchase> save(@RequestBody BetPurchase betPurchase) {
        BetPurchase savedBetPurchase = betPurchaseService.save(betPurchase);
        return new ResponseEntity<>(savedBetPurchase, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")UUID id) {
        return new ResponseEntity<>(this.betPurchaseService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }

}

