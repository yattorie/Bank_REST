package com.example.bankcards.controller;

import com.example.bankcards.dto.transfer.TransferRequest;
import com.example.bankcards.dto.transfer.TransferResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.TransferService;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TransferResponse> transfer(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TransferRequest requestDto) {
        String username = userDetails.getUsername();
        User user = userService.getByUsername(username);
        TransferResponse response = transferService.transferBetweenOwnCards(user, requestDto);
        return ResponseEntity.ok(response);
    }
}

