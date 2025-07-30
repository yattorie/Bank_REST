package com.example.bankcards.dto.card;

import com.example.bankcards.entity.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Response containing details of a card")
public class CardResponse {
    @Schema(
            description = "Unique identifier of the card",
            example = "1"
    )
    Long id;

    @Schema(
            description = "Masked card number (last 4 digits visible)",
            example = "**** **** **** 1234"
    )
    String maskedNumber;

    @Schema(
            description = "ID of the user who owns the card",
            example = "2"
    )
    Long ownerId;

    @Schema(
            description = "Expiration date of the card",
            example = "2028-06-01",
            format = "date"
    )
    LocalDate expirationDate;

    @Schema(
            description = "Current balance of the card",
            example = "1500.75",
            format = "decimal"
    )
    BigDecimal balance;

    @Schema(
            description = "Status of the card (ACTIVE, BLOCKED, EXPIRED)",
            example = "ACTIVE",
            allowableValues = {"ACTIVE", "BLOCKED", "EXPIRED"}
    )
    CardStatus status;
}