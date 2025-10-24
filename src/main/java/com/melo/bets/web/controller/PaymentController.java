// language: java
package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.payment.PaymentCreateDto;
import com.melo.bets.domain.dto.payment.PaymentDto;
import com.melo.bets.domain.service.PaymentService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Tag(name = "Payments", description = "Operations for creating, retrieving and deleting payments")
@RestController
@RequestMapping("/api/v1/payments")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Get all payments", description = "Returns a paginated list of payments")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.class)))
    })
    @GetMapping
    public ResponseEntity<Page<PaymentDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "paymentType") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return new ResponseEntity<>(paymentService.getAll(page, elements, sortBy, sortDirection), HttpStatus.OK);
    }

    @Operation(summary = "Get a payment by id", description = "Retrieve a single payment by its UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDto.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found with the provided ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getById(
            @Parameter(description = "UUID of the payment", required = true) @PathVariable UUID id) {
        return ResponseEntity.of(paymentService.getById(id));
    }

    @Operation(summary = "Get payments by user", description = "Returns paginated payments made by a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User payments retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "User or payments not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PaymentDto>> getByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @Parameter(description = "UUID of the user", required = true) @PathVariable UUID userId) {
        return new ResponseEntity<>(paymentService.getByUser(page, elements, userId), HttpStatus.OK);
    }

    @Operation(summary = "Create a payment", description = "Creates a new payment. Requires authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentCreateDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PaymentCreateDto> save(@Valid @RequestBody PaymentCreateDto payment) {
        return new ResponseEntity<>(paymentService.save(payment), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a payment", description = "Delete a payment by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found with the provided ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID of the payment to delete", required = true) @PathVariable UUID id) {
        return new ResponseEntity<>(paymentService.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
