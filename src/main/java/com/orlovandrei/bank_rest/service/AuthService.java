package com.orlovandrei.bank_rest.service;

import com.orlovandrei.bank_rest.dto.auth.LoginRequest;
import com.orlovandrei.bank_rest.dto.auth.RefreshTokenRequest;
import com.orlovandrei.bank_rest.dto.auth.RegisterRequest;
import com.orlovandrei.bank_rest.dto.auth.TokenPair;
import jakarta.transaction.Transactional;

public interface AuthService {
    @Transactional
    void register(RegisterRequest registerRequest);

    TokenPair login(LoginRequest loginRequest);

    TokenPair refreshToken(RefreshTokenRequest request);
}
