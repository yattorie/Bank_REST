package com.orlovandrei.bank_rest.dto.blockorder;

import com.orlovandrei.bank_rest.entity.enums.BlockOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Details of a card block request")
public class BlockOrderRequest {
    @Schema(
            description = "Unique identifier of the block request",
            example = "1"
    )
    Long id;

    @Schema(
            description = "ID of the card to be blocked",
            example = "2"
    )
    Long cardId;

    @Schema(
            description = "ID of the user who requested the block",
            example = "3"
    )
    Long requestedById;

    @Schema(
            description = "Status of the block request (PENDING, APPROVED, REJECTED)",
            example = "PENDING",
            allowableValues = {"PENDING", "APPROVED", "REJECTED"}
    )
    BlockOrderStatus status;
}