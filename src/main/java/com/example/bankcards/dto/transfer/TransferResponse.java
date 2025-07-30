package com.example.bankcards.dto.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Response containing the result of a card-to-card transfer")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferResponse {
    @Schema(
            description = "Indicates if the transfer was successful",
            example = "true"
    )
    boolean success;

    @Schema(
            description = "Message describing the outcome of the transfer",
            example = "Transfer successful"
    )
    String message;

    @Schema(
            description = "Timestamp when the transfer was processed",
            example = "2025-06-01T07:38:00",
            format = "date-time"
    )
    LocalDateTime timestamp;
}