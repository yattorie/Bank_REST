package com.orlovandrei.bank_rest.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Response containing details of a user")
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @Schema(
            description = "Unique identifier of the user",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Username of the user",
            example = "test_user"
    )
    String username;

    @Schema(
            description = "Email address of the user",
            example = "test_user@example.com"
    )
    String email;

    @Schema(
            description = "Role of the user (ROLE_USER, ROLE_ADMIN)",
            example = "ROLE_USER",
            allowableValues = {"ROLE_USER", "ROLE_ADMIN"}
    )
    String role;
}

