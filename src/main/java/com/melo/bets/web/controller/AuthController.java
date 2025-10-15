package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.service.AuthService;
import com.melo.bets.web.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and authorization")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user with email and password, returning a JWT token in the Authorization header"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User credentials for authentication",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginDto.class),
                    examples = @ExampleObject(
                            name = "Login example",
                            summary = "Example login request",
                            value = """
                                    {
                                      "email": "usuario@ejemplo.com",
                                      "password": "miPassword123"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Successful authentication. JWT token is returned in the Authorization header",
                    headers = @io.swagger.v3.oas.annotations.headers.Header(
                            name = HttpHeaders.AUTHORIZATION,
                            description = "JWT token for authentication",
                            schema = @Schema(type = "string")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data - validation errors",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - invalid email or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto loginDto) {
        String jwt = this.authService.authenticate(loginDto);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }
}
