package com.example.bankcards.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Schema(description = "Request for refreshing an access token using a refresh token")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenRequest {
    @Schema(
            description = "Refresh token used to generate a new access token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    String refreshToken;
}
