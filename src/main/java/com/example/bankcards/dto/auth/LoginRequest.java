package com.example.bankcards.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for user login authentication")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @Schema(
            description = "Username of the user attempting to log in",
            example = "new_user"
    )
    @NotBlank(message = "Username must be not null.")
    String username;

    @Schema(
            description = "Password for the user account",
            example = "password123"
    )
    @NotBlank(message = "Password must be not null.")
    String password;
}
