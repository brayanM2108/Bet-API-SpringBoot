package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<BetDto>> getAll() {
        return new ResponseEntity<>(betService.getAllBets(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<BetDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.of(betService.getBet(id));
    }
    @GetMapping("/available")
    public ResponseEntity <Page<BetDto>> getAvailable (@RequestParam (defaultValue = "0") int page,
                                                        @RequestParam (defaultValue = "8") int elements,
                                                        @RequestParam (defaultValue = "odds") String sortBy,
                                                        @RequestParam (defaultValue = "ASC") String sortDirection){
        return new ResponseEntity<>(betService.getAllAvailable(page, elements, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("/competition/{id}")
    public ResponseEntity<List<BetDto>> getByCompetition(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(betService.findByCompetition(id), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<BetDto>> getByCategory(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(betService.findByCategory(id), HttpStatus.OK);
    }

    @GetMapping("/competition-category")
    public ResponseEntity<List<BetDto>> getByCompetitionAndCategory(@RequestParam UUID competitionId,
                                                                 @RequestParam UUID categoryId) {
        return new ResponseEntity<>(betService.findByCompetitionAndCategory(competitionId, categoryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BetCreateDto> save(@RequestBody BetCreateDto bet) {
        return new ResponseEntity<>(betService.saveBet(bet), HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BetUpdateDto> update(@PathVariable ("id") UUID id, @RequestBody BetUpdateDto bet) {
        return betService.updateBet(id, bet)
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
