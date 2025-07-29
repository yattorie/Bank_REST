package com.orlovandrei.bank_rest.controller;

import com.orlovandrei.bank_rest.dto.auth.LoginRequest;
import com.orlovandrei.bank_rest.dto.auth.RefreshTokenRequest;
import com.orlovandrei.bank_rest.dto.auth.RegisterRequest;
import com.orlovandrei.bank_rest.dto.auth.TokenPair;
import com.orlovandrei.bank_rest.dto.success.SuccessResponse;
import com.orlovandrei.bank_rest.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<SuccessResponse> register(
            @Valid
            @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Registration successful"));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and obtain tokens")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody LoginRequest loginRequest) {
        TokenPair tokenPair = authService.login(loginRequest);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token using a refresh token")
    public ResponseEntity<?> refreshToken(
            @Valid
            @RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }
}
