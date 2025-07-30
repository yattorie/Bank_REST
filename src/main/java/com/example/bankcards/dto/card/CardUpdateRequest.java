package com.example.bankcards.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request for updating card details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardUpdateRequest {
    @Schema(
            description = "New expiration date for the card",
            example = "2028-06-01"
    )
    LocalDate expirationDate;

    @Schema(
            description = "New status for the card",
            example = "ACTIVE",
            allowableValues = {"ACTIVE", "BLOCKED", "EXPIRED"})
    String status;
}
