package com.orlovandrei.bank_rest.controller;

import com.orlovandrei.bank_rest.dto.auth.LoginRequest;
import com.orlovandrei.bank_rest.dto.auth.RefreshTokenRequest;
import com.orlovandrei.bank_rest.dto.auth.RegisterRequest;
import com.orlovandrei.bank_rest.dto.auth.TokenPair;
import com.orlovandrei.bank_rest.dto.success.SuccessResponse;
import com.orlovandrei.bank_rest.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void register_returnsCreatedSuccessResponse() {
        RegisterRequest request = new RegisterRequest("user", "user@mail.com", "pass");
        Mockito.doNothing().when(authService).register(request);

        ResponseEntity<SuccessResponse> response = authController.register(request);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Registration successful", response.getBody().getMessage());
        Mockito.verify(authService).register(request);
    }

    @Test
    void login_returnsTokenPair() {
        LoginRequest loginRequest = new LoginRequest("user", "pass");
        TokenPair tokenPair = new TokenPair("access", "refresh");
        Mockito.when(authService.login(loginRequest)).thenReturn(tokenPair);

        ResponseEntity<?> response = authController.login(loginRequest);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(tokenPair, response.getBody());
        Mockito.verify(authService).login(loginRequest);
    }

    @Test
    void refreshToken_returnsTokenPair() {
        RefreshTokenRequest request = new RefreshTokenRequest("refreshToken");
        TokenPair tokenPair = new TokenPair("access", "refreshToken");
        Mockito.when(authService.refreshToken(request)).thenReturn(tokenPair);

        ResponseEntity<?> response = authController.refreshToken(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(tokenPair, response.getBody());
        Mockito.verify(authService).refreshToken(request);
    }
}