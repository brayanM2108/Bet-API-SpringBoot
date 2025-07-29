package com.melo.bets.controller;

import com.melo.bets.domain.RaffleParticipation;
import com.melo.bets.domain.service.RaffleParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/raffle-participation")
public class RaffleParticipationController {

    private final RaffleParticipationService raffleParticipationService;

    @Autowired
    public RaffleParticipationController(RaffleParticipationService raffleParticipationService) {
        this.raffleParticipationService = raffleParticipationService;
    }

    @GetMapping
    public ResponseEntity<List<RaffleParticipation>> getAll() {
        return new ResponseEntity<>(raffleParticipationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/raffle/{id}")
    public ResponseEntity<List<RaffleParticipation>> getByRaffle(@PathVariable("id") UUID raffleId) {
        return new ResponseEntity<>(raffleParticipationService.getByRaffle(raffleId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<RaffleParticipation>> getByUser(@PathVariable("id") UUID userId) {
        return new ResponseEntity<>(raffleParticipationService.getByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/userandraffle")
    public ResponseEntity<RaffleParticipation> getByUserAndRaffle(@RequestParam UUID userId,
                                                                  @RequestParam UUID raffleId) {
        return raffleParticipationService.getByUserAndRaffle(userId, raffleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RaffleParticipation> save(@RequestBody RaffleParticipation raffleParticipation) {
        RaffleParticipation savedRaffleParticipation = raffleParticipationService.save(raffleParticipation);
        return new ResponseEntity<>(savedRaffleParticipation, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(raffleParticipationService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }
}