package com.orlovandrei.bank_rest.controller;

import com.orlovandrei.bank_rest.dto.transfer.TransferRequest;
import com.orlovandrei.bank_rest.dto.transfer.TransferResponse;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.service.TransferService;
import com.orlovandrei.bank_rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfer Controller", description = "Transfer API")
public class TransferController {

    private final TransferService transferService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Transfer funds between user's own cards")
    public TransferResponse transfer(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TransferRequest requestDto) {
        String username = userDetails.getUsername();
        User user = userService.getByUsername(username);
        return transferService.transferBetweenOwnCards(user, requestDto);
    }
}

