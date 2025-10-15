package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.user.UserDto;
import com.melo.bets.domain.dto.user.UserRegisterDto;
import com.melo.bets.domain.service.UserService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints for managing user accounts and operations")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(
            summary = "Get all users (paginated)",
            description = "Returns a paginated list of all registered users. Supports pagination and page size configuration."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved paginated users list",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            )
    })
    public ResponseEntity<Page<UserDto>> getAll(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of elements per page", example = "10")
            @RequestParam(defaultValue = "10") int elements) {
        return new ResponseEntity<>(userService.getAll(page, elements), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Returns a specific user by their unique identifier (UUID)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<UserDto> getById(
            @Parameter(
                    description = "User unique identifier (UUID format)",
                    required = true,
                    example = "a4483993-79d3-4adf-8e9a-5f7a89d9ee98"
            )
            @PathVariable("id") UUID id) {
        return ResponseEntity.of(userService.getById(id));
    }

    @PostMapping
    @Operation(
            summary = "Register new user",
            description = "Creates a new user account in the system with the provided information."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User registration data including name, email, password and document",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserRegisterDto.class),
                    examples = @ExampleObject(
                            name = "Complete user registration",
                            summary = "Example of a complete user registration request",
                            value = """
                                    {
                                      "name": "example name",
                                      "email": "user@example.com",
                                      "password": "miPassword123",
                                      "document": "0123456789"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegisterDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data - validation errors (e.g., invalid email format, weak password)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Validation error",
                                    value = """
                                            {
                                              "error": "ValidationError",
                                              "status": 400,
                                              "timestamp": "2025-01-20T10:30:00.000+00:00",
                                              "path": "/api/v1/users"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email or document already exists in the system",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Duplicate email",
                                    value = """
                                            {
                                              "error": "EmailAlreadyExists",
                                              "status": 409,
                                              "timestamp": "2025-01-20T10:30:00.000+00:00",
                                              "path": "/api/v1/users"
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<UserRegisterDto> save(@Valid @RequestBody UserRegisterDto user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user",
            description = "Permanently removes a user from the system by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found with the provided ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "Unique identifier of the user to delete (UUID format)",
                    required = true,
                    example = "a4483993-79d3-4adf-8e9a-5f7a89d9ee98"
            )
            @PathVariable("id") UUID id) {
        return new ResponseEntity<>(userService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }
}
