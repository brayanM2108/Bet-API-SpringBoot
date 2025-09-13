package com.melo.bets.web.controller;


import com.melo.bets.domain.dto.betPurchase.*;
import com.melo.bets.domain.service.BetPurchaseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bet-purchases")
public class BetPurchaseController {

    private final BetPurchaseService betPurchaseService;


    public BetPurchaseController(BetPurchaseService betPurchaseService) {
        this.betPurchaseService = betPurchaseService;
    }

    @GetMapping
    public ResponseEntity <Page<BetPurchaseDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size)  {
        return new ResponseEntity<> (betPurchaseService.getAll(page, size), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity <BetPurchaseDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.of(betPurchaseService.getById(id));
    }

    @GetMapping("/bet/{id}")
    public ResponseEntity <List<BetPurchaseDto>> getByBet(@PathVariable ("id") UUID betId) {
        return new ResponseEntity<> (betPurchaseService.getByBet(betId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity <Page<BetPurchaseUserDetailsDto>> getByUser(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      @PathVariable ("id") UUID userId) {
        return new ResponseEntity<> (betPurchaseService.getByUser(page, size, userId), HttpStatus.OK);
    }

    @GetMapping("/creator/{id}")
    public ResponseEntity <Page<BetPurchaseCreatorDetailsDto>> getByCreator(@RequestParam (defaultValue = "0") int page,
                                                                            @RequestParam (defaultValue = "10") int size,
                                                                            @PathVariable ("id") UUID creatorId) {
        return new ResponseEntity<> (betPurchaseService.getByCreatorId(page, size, creatorId), HttpStatus.OK);
    }

    @GetMapping("/userandbet")
    public ResponseEntity<BetPurchaseDto> getByUserAndBet(@RequestParam UUID userId,
                                                       @RequestParam UUID betId) {
        return betPurchaseService.getByUserAndBet(userId, betId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BetPurchaseCreateResponseDto> save(@Valid @RequestBody BetPurchaseCreateDto betPurchase) {
        BetPurchaseCreateResponseDto savedBetPurchase = betPurchaseService.save(betPurchase);
        return new ResponseEntity<>(savedBetPurchase, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")UUID id) {
        return new ResponseEntity<>(this.betPurchaseService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }

}

