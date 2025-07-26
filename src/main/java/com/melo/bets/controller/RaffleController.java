package com.melo.bets.controller;


import com.melo.bets.domain.Raffle;
import com.melo.bets.domain.service.RaffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/raffles")
public class RaffleController {

    private final RaffleService raffleService;
    @Autowired
    public RaffleController(RaffleService raffleService) {
        this.raffleService = raffleService;
    }

    @GetMapping
    public ResponseEntity<List<Raffle>> getRaffle() {
        return new ResponseEntity<>(raffleService.getAllRaffles(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Raffle> getRaffleById(@PathVariable ("id") UUID id) {
        return ResponseEntity.of(raffleService.getRaffle(id));
    }

    @PostMapping
    public ResponseEntity<Raffle> saveRaffle(@RequestBody Raffle raffle) {
        return new ResponseEntity<>(raffleService.saveRaffle(raffle), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Raffle> updateRaffle(@RequestBody Raffle raffle) {
        return raffleService.updateRaffle(raffle)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.raffleService.deleteRaffle(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }

}
