package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.service.BetService;
import com.melo.bets.security.user.UserDetailsWithId;
import com.melo.bets.web.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bets")
@Tag(name = "Bet", description = "Endpoints for managing bets and operations")
public class BetController {

    private final BetService betService;

    @Autowired
    public BetController(BetService betService) {
        this.betService = betService;
    }


    @Operation(
            summary = "Get all bets (paginated)",
            description = "Returns a paginated list of all bets."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BetDto.class)))
    })
    @GetMapping
    public ResponseEntity<Page<BetDto>> getAll(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page", example = "10")
            @RequestParam(defaultValue = "10") int elements) {
        return new ResponseEntity<>(betService.getAll(page, elements), HttpStatus.OK);
    }

    @Operation(
            summary = "Get bet by ID",
            description = "Returns a bet by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bet found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<BetDto> getById(
            @Parameter(description = "Bet ID", required = true, example = "193c9d9c-3b03-443e-b94b-c801e28f27f9")
            @PathVariable("id") UUID id) {
        return ResponseEntity.of(betService.get(id));
    }

    @Operation(
            summary = "Get available bets (paginated and sorted)",
            description = "Returns a paginated and sorted list of available bets."
    )
    @GetMapping("/available")
    public ResponseEntity<Page<BetDto>> getAvailable(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page", example = "8")
            @RequestParam(defaultValue = "8") int elements,
            @Parameter(description = "Field to sort by", example = "odds")
            @RequestParam(defaultValue = "odds") String sortBy,
            @Parameter(description = "Sort direction (ASC or DESC)", example = "ASC")
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return new ResponseEntity<>(betService.getAllAvailable(page, elements, sortBy, sortDirection), HttpStatus.OK);
    }

    @Operation(
            summary = "Get bets by competition (paginated)",
            description = "Returns a paginated list of bets for a specific competition."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bets found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bets not found with the provided competitionID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/competition/{id}")
    public ResponseEntity<Page<BetDto>> getByCompetition(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page", example = "8")
            @RequestParam(defaultValue = "8") int elements,
            @Parameter(description = "Competition ID", required = true)
            @PathVariable("id") UUID id) {
        return new ResponseEntity<>(betService.findByCompetition(page, elements, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get bets by category (paginated)",
            description = "Returns a paginated list of bets for a specific category."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bet found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bets not found with the provided categoryID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<Page<BetDto>> getByCategory(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page", example = "8")
            @RequestParam(defaultValue = "8") int elements,
            @Parameter(description = "Category ID", required = true)
            @PathVariable("id") UUID id) {
        return new ResponseEntity<>(betService.findByCategory(page, elements, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new bet",
            description = "Creates a new bet. Optionally, an image file can be uploaded."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Bet created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BetCreateDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data - validation errors (e.g., invalid date, price, etc)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Validation error",
                                    value = """
                                                    {
                                                      "error": "MethodArgumentNotValidException",
                                                      "status": 400,
                                                      "timestamp": "2025-01-20T10:30:00.000+00:00",
                                                      "path": "/api/v1/bets"
                                                    }
                                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet not found with the provided categoryID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
    )})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BetCreateDto> save(
            @Parameter(description = "Bet data", required = true)
            @Valid @RequestPart("bet") BetCreateDto bet,
            @AuthenticationPrincipal UserDetailsWithId userDetails,
            @Parameter(description = "Optional image file")
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws Exception {
        UUID userId = userDetails.getUserId();
        return new ResponseEntity<>(betService.saveBet(bet, userId, imageFile), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a bet",
            description = "Updates an existing bet by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Bet updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BetDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data - validation errors (e.g., invalid date, price, etc)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Validation error",
                                    value = """
                                                    {
                                                      "error": "MethodArgumentNotValidException",
                                                      "status": 400,
                                                      "timestamp": "2025-01-20T10:30:00.000+00:00",
                                                      "path": "/api/v1/bets"
                                                    }
                                                    """
                            )
                    )
            )})
    @PatchMapping("{id}")
    public ResponseEntity<BetDto> update(
            @Parameter(description = "Bet ID", required = true)
            @PathVariable ("id") UUID id,
            @Parameter(description = "Bet update data", required = true)
            @Valid @RequestBody BetUpdateDto bet) {
        return betService.update(id, bet)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Delete a bet",
            description = "Deletes a bet by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bet delete"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bet not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Bet ID", required = true)
            @PathVariable("id") UUID id) {
        betService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
