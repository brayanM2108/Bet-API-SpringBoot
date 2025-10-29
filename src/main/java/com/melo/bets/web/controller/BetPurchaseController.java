package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.betPurchase.*;
import com.melo.bets.domain.service.BetPurchaseService;
import com.melo.bets.web.config.UserDetailsWithId;
import com.melo.bets.web.exception.ErrorResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Tag(name = "Bet Purchases", description = "Operations for creating, retrieving and deleting bet purchases")
@RestController
@RequestMapping("/api/v1/bet-purchases")
@SecurityRequirement(name = "bearerAuth")
public class BetPurchaseController {

    private final BetPurchaseService betPurchaseService;

    public BetPurchaseController(BetPurchaseService betPurchaseService) {
        this.betPurchaseService = betPurchaseService;
    }

    @Operation(
            summary = "Get all bet purchases",
            description = "Returns a paginated list of bet purchases"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BetPurchaseDto.class)))
    })
    @GetMapping
    public ResponseEntity<Page<BetPurchaseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements) {
        return new ResponseEntity<>(betPurchaseService.getAll(page, elements), HttpStatus.OK);
    }

    @Operation(
            summary = "Get a bet purchase by id",
            description = "Retrieve a single bet purchase by its UUID")

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bet purchase found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet Purchase not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<BetPurchaseDto> getById(
            @Parameter(description = "UUID of the bet purchase", required = true) @PathVariable("id") UUID id) {
        return ResponseEntity.of(betPurchaseService.getById(id));
    }

    @Operation(summary = "Get purchases by bet", description = "List all purchases for a specific bet")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchases retrieved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet Purchase not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/bet/{id}")
    public ResponseEntity<List<BetPurchaseDto>> getByBet(
            @Parameter(description = "UUID of the bet", required = true) @PathVariable("id") UUID betId) {
        return new ResponseEntity<>(betPurchaseService.getByBet(betId), HttpStatus.OK);
    }

    @Operation(summary = "Get purchases by user", description = "Return paginated purchases made by a user")

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User purchases retrieved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet Purchase not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )

    })
    @GetMapping("/user/{id}")
    public ResponseEntity<Page<BetPurchaseUserDetailsDto>> getByUser(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @Parameter(description = "UUID of the user", required = true) @PathVariable("id") UUID userId) {
        return new ResponseEntity<>(betPurchaseService.getByUser(page, size, userId), HttpStatus.OK);
    }

    @Operation(summary = "Get purchases by creator", description = "Return paginated purchases for bets created by a specific creator")

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Creator purchases retrieved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet Purchase not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )

    })
    @GetMapping("/creator/{id}")
    public ResponseEntity<Page<BetPurchaseCreatorDetailsDto>> getByCreator(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @Parameter(description = "UUID of the creator", required = true) @PathVariable("id") UUID creatorId) {
        return new ResponseEntity<>(betPurchaseService.getByCreatorId(page, size, creatorId), HttpStatus.OK);
    }

    @Operation(summary = "Get purchase by user and bet", description = "Retrieve a purchase by both user UUID and bet UUID")

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchase found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet Purchase not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/userandbet")
    public ResponseEntity<BetPurchaseDto> getByUserAndBet(
            @RequestParam UUID userId,
            @RequestParam UUID betId) {
        return betPurchaseService.getByUserAndBet(userId, betId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a bet purchase", description = "Create a new bet purchase. Requires authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bet purchase created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet or User not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
    })
    @PostMapping("{betId}")
    public ResponseEntity<BetPurchaseCreateResponseDto> purchaseBet(
            @Parameter(description = "UUID of the bet to purchase", required = true)
            @PathVariable UUID betId) {

        BetPurchaseCreateResponseDto savedBetPurchase = betPurchaseService.save(betId);
        return new ResponseEntity<>(savedBetPurchase, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a bet purchase", description = "Delete a bet purchase by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet Purchase not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the bet purchase to delete", required = true) @PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.betPurchaseService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }

}
