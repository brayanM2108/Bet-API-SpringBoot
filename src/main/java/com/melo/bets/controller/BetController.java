package com.melo.bets.controller;

import com.melo.bets.domain.Bet;
import com.melo.bets.domain.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bets")
public class BetController {

    private final BetService betService;

    @Autowired
    public BetController(BetService betService) {
        this.betService = betService;
    }

    @GetMapping
    public ResponseEntity<List<Bet>> getAllBets() {
        return new ResponseEntity<>(betService.getAllBets(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<Bet> getBetById(@PathVariable("id") UUID id) {
        return ResponseEntity.of(betService.getBet(id));
    }

    @PostMapping
    public ResponseEntity<Bet> saveBet(@RequestBody Bet bet) {
        return new ResponseEntity<>(betService.saveBet(bet), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Bet> updateBet(@RequestBody Bet bet) {
        return betService.updateBet(bet)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.betService.deleteBet(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }

}
