package com.orlovandrei.bank_rest.controller;

import com.orlovandrei.bank_rest.dto.balance.BalanceResponse;
import com.orlovandrei.bank_rest.dto.blockorder.BlockOrderRequest;
import com.orlovandrei.bank_rest.dto.card.CardRequest;
import com.orlovandrei.bank_rest.dto.card.CardResponse;
import com.orlovandrei.bank_rest.dto.card.CardUpdateRequest;
import com.orlovandrei.bank_rest.dto.deposit.DepositRequest;
import com.orlovandrei.bank_rest.dto.deposit.DepositResponse;
import com.orlovandrei.bank_rest.dto.mapper.BlockOrderMapper;
import com.orlovandrei.bank_rest.dto.mapper.CardMapper;
import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.service.BlockOrderService;
import com.orlovandrei.bank_rest.service.CardService;
import com.orlovandrei.bank_rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@Tag(name = "Card Controller", description = "Card API")
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;
    private final BlockOrderService blockOrderService;
    private final BlockOrderMapper blockOrderMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get card by id")
    public ResponseEntity<CardResponse> getCardById(
            @PathVariable Long id) {
        Card card = cardService.findById(id);
        return ResponseEntity.ok(cardMapper.toDto(card));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all cards")
    public ResponseEntity<Page<CardResponse>> getAllCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Card> cards = cardService.findAll(pageable);
        Page<CardResponse> result = cards.map(cardMapper::toDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete card")
    public ResponseEntity<?> deleteCard(
            @PathVariable Long id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Block card")
    public ResponseEntity<Void> blockCard(
            @PathVariable Long id) {
        cardService.block(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activate card")
    public ResponseEntity<Void> activateCard(
            @PathVariable Long id) {
        cardService.activate(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new card")
    public ResponseEntity<CardResponse> createCard(
            @Valid
            @RequestBody CardRequest request) {
        Card card = cardService.createCard(request);
        return ResponseEntity.ok(cardMapper.toDto(card));
    }

    @PostMapping("/{id}/request-block")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Submit a request to block the card")
    public ResponseEntity<BlockOrderRequest> requestBlock(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id) {
        String username = principal.getUsername();
        User user = userService.getByUsername(username);
        Card card = cardService.findById(id);
        BlockOrderRequest dto = blockOrderMapper.toDto(blockOrderService.createBlockRequest(card, user));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/{id}/deposit")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Top up the card for the current user")
    public ResponseEntity<DepositResponse> deposit(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id,
            @Valid @RequestBody DepositRequest request) {
        String username = principal.getUsername();
        DepositResponse response = cardService.deposit(id, username, request.getAmount());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/balance")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "View card balance for the current user")
    public ResponseEntity<BalanceResponse> getBalance(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id) {
        String username = principal.getUsername();
        BalanceResponse response = cardService.getBalanceResponse(id, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "View cards for the current user")
    public ResponseEntity<Page<CardResponse>> getMyCards(
            @AuthenticationPrincipal UserDetails principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String username = principal.getUsername();
        User user = userService.getByUsername(username);
        Pageable pageable = PageRequest.of(page, size);
        Page<Card> cards = cardService.findByOwner(user, pageable);
        return ResponseEntity.ok(cards.map(cardMapper::toDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update card details")
    public ResponseEntity<CardResponse> updateCard(
            @PathVariable Long id,
            @Valid @RequestBody CardUpdateRequest request) {
        Card updatedCard = cardService.updateCard(id, request);
        return ResponseEntity.ok(cardMapper.toDto(updatedCard));
    }
}