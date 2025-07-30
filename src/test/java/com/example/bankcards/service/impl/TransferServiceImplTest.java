package com.example.bankcards.service.impl;

import com.example.bankcards.dto.transfer.TransferRequest;
import com.example.bankcards.dto.transfer.TransferResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.AmountExceedsLimitException;
import com.example.bankcards.exception.CardInactiveException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.InvalidCardOwnerException;
import com.example.bankcards.exception.TransferBetweenSameCardException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    void transferBetweenOwnCards_success_returnsTransferResponse() {
        User user = User.builder().id(1L).username("testuser").build();
        Card fromCard = Card.builder()
                .id(1L)
                .owner(user)
                .balance(new BigDecimal("100.00"))
                .status(CardStatus.ACTIVE)
                .build();
        Card toCard = Card.builder()
                .id(2L)
                .owner(user)
                .balance(new BigDecimal("50.00"))
                .status(CardStatus.ACTIVE)
                .build();
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));
        Mockito.when(cardRepository.save(fromCard)).thenReturn(fromCard);
        Mockito.when(cardRepository.save(toCard)).thenReturn(toCard);
        Mockito.when(transferRepository.save(Mockito.any())).thenReturn(Mockito.any());

        TransferResponse response = transferService.transferBetweenOwnCards(user, request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Transfer successful", response.getMessage());
        Assertions.assertEquals(new BigDecimal("50.00"), fromCard.getBalance());
        Assertions.assertEquals(new BigDecimal("100.00"), toCard.getBalance());
        Mockito.verify(cardRepository).save(fromCard);
        Mockito.verify(cardRepository).save(toCard);
        Mockito.verify(transferRepository).save(Mockito.any());
    }

    @Test
    void transferBetweenOwnCards_sameCard_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        TransferRequest request = new TransferRequest(1L, 1L, new BigDecimal("50.00"));

        Assertions.assertThrows(TransferBetweenSameCardException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }

    @Test
    void transferBetweenOwnCards_fromCardNotFound_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(CardNotFoundException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }

    @Test
    void transferBetweenOwnCards_toCardNotFound_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        Card fromCard = Card.builder()
                .id(1L)
                .owner(user)
                .balance(new BigDecimal("100.00"))
                .status(CardStatus.ACTIVE)
                .build();
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(CardNotFoundException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }

    @Test
    void transferBetweenOwnCards_invalidOwner_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        User otherUser = User.builder().id(2L).username("otheruser").build();
        Card fromCard = Card.builder()
                .id(1L)
                .owner(otherUser)
                .balance(new BigDecimal("100.00"))
                .status(CardStatus.ACTIVE)
                .build();

        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));

        Assertions.assertThrows(InvalidCardOwnerException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }


    @Test
    void transferBetweenOwnCards_inactiveFromCard_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        Card fromCard = Card.builder()
                .id(1L)
                .owner(user)
                .balance(new BigDecimal("100.00"))
                .status(CardStatus.BLOCKED)
                .build();
        Card toCard = Card.builder()
                .id(2L)
                .owner(user)
                .balance(new BigDecimal("50.00"))
                .status(CardStatus.ACTIVE)
                .build();
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        Assertions.assertThrows(CardInactiveException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }

    @Test
    void transferBetweenOwnCards_inactiveToCard_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        Card fromCard = Card.builder()
                .id(1L)
                .owner(user)
                .balance(new BigDecimal("100.00"))
                .status(CardStatus.ACTIVE)
                .build();
        Card toCard = Card.builder()
                .id(2L)
                .owner(user)
                .balance(new BigDecimal("50.00"))
                .status(CardStatus.BLOCKED)
                .build();
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        Assertions.assertThrows(CardInactiveException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }

    @Test
    void transferBetweenOwnCards_amountExceedsLimit_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        Card fromCard = Card.builder()
                .id(1L)
                .owner(user)
                .balance(new BigDecimal("100000.00"))
                .status(CardStatus.ACTIVE)
                .build();
        Card toCard = Card.builder()
                .id(2L)
                .owner(user)
                .balance(new BigDecimal("50.00"))
                .status(CardStatus.ACTIVE)
                .build();
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("15000.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        Assertions.assertThrows(AmountExceedsLimitException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }

    @Test
    void transferBetweenOwnCards_insufficientFunds_throwsException() {
        User user = User.builder().id(1L).username("testuser").build();
        Card fromCard = Card.builder()
                .id(1L)
                .owner(user)
                .balance(new BigDecimal("10.00"))
                .status(CardStatus.ACTIVE)
                .build();
        Card toCard = Card.builder()
                .id(2L)
                .owner(user)
                .balance(new BigDecimal("50.00"))
                .status(CardStatus.ACTIVE)
                .build();
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        Assertions.assertThrows(InsufficientFundsException.class, () ->
                transferService.transferBetweenOwnCards(user, request));
    }
}