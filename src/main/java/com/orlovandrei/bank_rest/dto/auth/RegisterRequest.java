package com.orlovandrei.bank_rest.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for registering a new user account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @Schema(
            description = "Unique username for the new user",
            example = "test_user"
    )
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @NotBlank(message = "Username must be not null.")
    String username;

    @Schema(
            description = "Email address for the new user",
            example = "test_user@example.com"
    )
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    @NotBlank(message = "Email must be not null.")
    String email;

    @Schema(
            description = "Password for the new user account",
            example = "password123"
    )
    @NotBlank(message = "Password must be not null.")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String password;
}
