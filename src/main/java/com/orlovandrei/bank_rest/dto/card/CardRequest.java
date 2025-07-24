package com.orlovandrei.bank_rest.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request for creating a new card")
public class CardRequest {
    @Schema(
            description = "ID of the user who will own the card",
            example = "1"
    )

    @NotNull(message = "Owner id must not be null")
    Long ownerId;
}