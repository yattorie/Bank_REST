package com.example.bankcards.service.impl;

import com.example.bankcards.dto.auth.LoginRequest;
import com.example.bankcards.dto.auth.RefreshTokenRequest;
import com.example.bankcards.dto.auth.RegisterRequest;
import com.example.bankcards.dto.auth.TokenPair;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.Role;
import com.example.bankcards.exception.EmailAlreadyExistsException;
import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_success() {
        RegisterRequest request = new RegisterRequest("newuser", "newuser@example.com", "password");
        Mockito.when(userRepository.existsByUsername("newuser")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        User savedUser = User.builder()
                .username("newuser")
                .email("newuser@example.com")
                .password("encodedPassword")
                .role(Role.ROLE_USER)
                .build();
        Mockito.when(userRepository.save(any(User.class))).thenReturn(savedUser);

        authService.register(request);

        Mockito.verify(userRepository).save(argThat(user ->
                user.getUsername().equals("newuser") &&
                        user.getEmail().equals("newuser@example.com") &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getRole() == Role.ROLE_USER));
    }

    @Test
    void register_throwsUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest("existinguser", "newemail@example.com", "password");
        Mockito.when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));
    }

    @Test
    void register_throwsEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("newuser", "existingemail@example.com", "password");
        Mockito.when(userRepository.existsByUsername("newuser")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("existingemail@example.com")).thenReturn(true);

        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> authService.register(request));
    }

    @Test
    void login_success() {
        LoginRequest request = new LoginRequest("user", "password");
        Authentication authentication = mock(Authentication.class);
        TokenPair tokenPair = new TokenPair("accessToken", "refreshToken");
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        Mockito.when(jwtService.generateTokenPair(authentication)).thenReturn(tokenPair);

        TokenPair result = authService.login(request);

        Assertions.assertEquals(tokenPair, result);
        Mockito.verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtService).generateTokenPair(authentication);
    }

    @Test
    void refreshToken_success() {
        RefreshTokenRequest request = new RefreshTokenRequest("refreshToken");
        UserDetails userDetails = mock(UserDetails.class);
        Mockito.when(jwtService.isRefreshToken("refreshToken")).thenReturn(true);
        Mockito.when(jwtService.extractUsernameFromToken("refreshToken")).thenReturn("user");
        Mockito.when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        Mockito.when(jwtService.generateAccessToken(any(Authentication.class))).thenReturn("newAccessToken");

        TokenPair result = authService.refreshToken(request);

        Assertions.assertEquals("newAccessToken", result.getAccessToken());
        Assertions.assertEquals("refreshToken", result.getRefreshToken());
    }

    @Test
    void refreshToken_throwsInvalidRefreshToken() {
        RefreshTokenRequest request = new RefreshTokenRequest("invalidToken");
        Mockito.when(jwtService.isRefreshToken("invalidToken")).thenReturn(false);

        Assertions.assertThrows(IllegalArgumentException.class, () -> authService.refreshToken(request));
    }

    @Test
    void refreshToken_throwsUserNotFound() {
        RefreshTokenRequest request = new RefreshTokenRequest("refreshToken");
        Mockito.when(jwtService.isRefreshToken("refreshToken")).thenReturn(true);
        Mockito.when(jwtService.extractUsernameFromToken("refreshToken")).thenReturn("user");
        Mockito.when(userDetailsService.loadUserByUsername("user")).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> authService.refreshToken(request));
    }
}