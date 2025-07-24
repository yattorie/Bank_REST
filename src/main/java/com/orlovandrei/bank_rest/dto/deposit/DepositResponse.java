package com.orlovandrei.bank_rest.dto.deposit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Response containing the result of a deposit operation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositResponse {
    @Schema(
            description = "Indicates if the deposit was successful",
            example = "true"
    )
    boolean success;

    @Schema(
            description = "Message describing the outcome of the deposit",
            example = "Deposit successful"
    )
    String message;

    @Schema(
            description = "New balance of the card after the deposit",
            example = "1600.25",
            format = "decimal"
    )
    BigDecimal newBalance;
}

