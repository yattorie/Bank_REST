package com.example.bankcards.dto.balance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Response containing the balance information for a card")
public class BalanceResponse {
    @Schema(
            description = "Indicates if the balance retrieval was successful",
            example = "true"
    )
    boolean success;

    @Schema(
            description = "Message describing the outcome of the balance retrieval",
            example = "Balance retrieved successfully"
    )
    String message;

    @Schema(
            description = "Current balance of the card",
            example = "2434.11",
            format = "decimal"
    )
    BigDecimal balance;
}
