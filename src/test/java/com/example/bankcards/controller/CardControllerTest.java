package com.example.bankcards.controller;

import com.example.bankcards.dto.balance.BalanceResponse;
import com.example.bankcards.dto.blockorder.BlockOrderRequest;
import com.example.bankcards.dto.card.CardCreateRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.dto.card.CardUpdateRequest;
import com.example.bankcards.dto.deposit.DepositRequest;
import com.example.bankcards.dto.deposit.DepositResponse;
import com.example.bankcards.dto.mapper.BlockOrderMapper;
import com.example.bankcards.dto.mapper.CardMapper;
import com.example.bankcards.entity.BlockOrder;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.BlockOrderService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @Mock
    private CardService cardService;

    @Mock
    private CardMapper cardMapper;

    @Mock
    private BlockOrderService blockOrderService;

    @Mock
    private BlockOrderMapper blockOrderMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private CardController cardController;

    @Test
    void getCardById_returnsCardResponse() {
        CardResponse expected = new CardResponse();
        Mockito.when(cardService.findById(anyLong())).thenReturn(new Card());
        Mockito.when(cardMapper.toDto(any(Card.class))).thenReturn(expected);

        ResponseEntity<CardResponse> response = cardController.getCardById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void getAllCards_returnsPageOfCards() {
        Page<CardResponse> expected = new PageImpl<>(Collections.emptyList());

        Mockito.when(cardService.findAll(any(Pageable.class))).thenReturn(Page.empty());

        ResponseEntity<Page<CardResponse>> response =
                cardController.getAllCards(0, 10);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void deleteCard_returnsNoContent() {
        ResponseEntity<?> response = cardController.deleteCard(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(cardService).delete(1L);
    }

    @Test
    void blockCard_returnsOk() {
        ResponseEntity<Void> response = cardController.blockCard(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(cardService).block(1L);
    }

    @Test
    void activateCard_returnsOk() {
        ResponseEntity<Void> response = cardController.activateCard(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(cardService).activate(1L);
    }

    @Test
    void createCard_returnsCreatedCard() {
        CardCreateRequest request = new CardCreateRequest(1L);
        CardResponse expected = new CardResponse();

        Mockito.when(cardService.createCard(any(CardCreateRequest.class))).thenReturn(new Card());
        Mockito.when(cardMapper.toDto(any(Card.class))).thenReturn(expected);

        ResponseEntity<CardResponse> response = cardController.createCard(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void requestBlock_returnsCreatedRequest() {
        BlockOrderRequest expected = new BlockOrderRequest();
        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(userService.getByUsername(anyString())).thenReturn(new User());
        Mockito.when(cardService.findById(anyLong())).thenReturn(new Card());
        Mockito.when(blockOrderService.createBlockRequest(any(Card.class), any(User.class)))
                .thenReturn(new BlockOrder());
        Mockito.when(blockOrderMapper.toDto(any(BlockOrder.class))).thenReturn(expected);

        ResponseEntity<BlockOrderRequest> response =
                cardController.requestBlock(userDetails, 1L);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void deposit_returnsDepositResponse() {
        DepositRequest request = new DepositRequest(BigDecimal.TEN);
        DepositResponse expected = new DepositResponse(true, "Success", BigDecimal.valueOf(100));

        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(cardService.deposit(anyLong(), anyString(), any(BigDecimal.class)))
                .thenReturn(expected);

        ResponseEntity<DepositResponse> response =
                cardController.deposit(userDetails, 1L, request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void getBalance_returnsBalanceResponse() {
        BalanceResponse expected = new BalanceResponse(true, "Success", BigDecimal.valueOf(100));

        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(cardService.getBalanceResponse(anyLong(), anyString()))
                .thenReturn(expected);

        ResponseEntity<BalanceResponse> response =
                cardController.getBalance(userDetails, 1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void getMyCards_returnsUserCards() {
        CardResponse cardResponse = new CardResponse();
        Page<CardResponse> expected = new PageImpl<>(List.of(cardResponse));

        User user = new User();
        Page<Card> cardsPage = new PageImpl<>(List.of(new Card()));

        Mockito.when(userDetails.getUsername()).thenReturn("user");
        Mockito.when(userService.getByUsername("user")).thenReturn(user);
        Mockito.when(cardService.findByOwner(user, PageRequest.of(0, 10)))
                .thenReturn(cardsPage);
        Mockito.when(cardMapper.toDto(any(Card.class))).thenReturn(cardResponse);

        ResponseEntity<Page<CardResponse>> response =
                cardController.getMyCards(userDetails, 0, 10);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().getContent().size());
        Assertions.assertEquals(cardResponse, response.getBody().getContent().get(0));
    }
    @Test
    void updateCard_returnsUpdatedCard() {
        CardUpdateRequest request = new CardUpdateRequest(LocalDate.now(), "ACTIVE");
        CardResponse expected = new CardResponse();

        Mockito.when(cardService.updateCard(anyLong(), any(CardUpdateRequest.class)))
                .thenReturn(new Card());
        Mockito.when(cardMapper.toDto(any(Card.class))).thenReturn(expected);

        ResponseEntity<CardResponse> response =
                cardController.updateCard(1L, request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
    }
}