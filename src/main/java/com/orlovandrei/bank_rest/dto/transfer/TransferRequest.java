package com.orlovandrei.bank_rest.dto.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Schema(description = "Request for transferring funds between cards")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TransferRequest {
    @Schema(
            description = "ID of the source card for the transfer",
            example = "1"
    )
    Long fromCardId;

    @Schema(
            description = "ID of the target card for the transfer",
            example = "2"
    )
    Long toCardId;

    @Schema(
            description = "Amount to transfer between cards",
            example = "50.00",
            format = "decimal"
    )
    BigDecimal amount;
}