package com.orlovandrei.bank_rest.dto.deposit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Schema(description = "Request for depositing funds to a card")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    @NotNull
    @Positive(message = "Deposit amount must be positive")
    @Schema(
            description = "Amount to deposit to the card",
            example = "100.50",
            format = "decimal"
    )
    BigDecimal amount;
}
