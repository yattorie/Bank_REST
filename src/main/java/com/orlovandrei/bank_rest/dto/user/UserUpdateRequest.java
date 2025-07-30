package com.orlovandrei.bank_rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for updating an existing user")
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @Schema(description = "Updated username for the user",
            example = "updated_username"
    )
    @Nullable
    @Size(max = 255, message = "Username must be smaller than 255 characters.")
    String username;

    @Nullable
    @Schema(description = "Updated email address for the user",
            example = "new_email@example.com"
    )
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must be smaller than 255 characters.")
    String email;

    @Nullable
    @Schema(description = "Updated password for the user",
            example = "securePassword1234"
    )
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Nullable
    @Schema(description = "Updated role for the user",
            example = "ROLE_ADMIN"
    )
    String role;
}