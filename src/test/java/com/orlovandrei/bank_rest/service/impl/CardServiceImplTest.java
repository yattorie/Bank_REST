package com.orlovandrei.bank_rest.service.impl;

import com.orlovandrei.bank_rest.dto.balance.BalanceResponse;
import com.orlovandrei.bank_rest.dto.card.CardCreateRequest;
import com.orlovandrei.bank_rest.dto.card.CardUpdateRequest;
import com.orlovandrei.bank_rest.dto.deposit.DepositResponse;
import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.entity.enums.CardStatus;
import com.orlovandrei.bank_rest.exception.*;
import com.orlovandrei.bank_rest.repository.CardRepository;
import com.orlovandrei.bank_rest.repository.UserRepository;
import com.orlovandrei.bank_rest.util.Messages;
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
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void findById_success() {
        Card expected = new Card();
        when(cardRepository.findById(1L)).thenReturn(Optional.of(expected));

        Card result = cardService.findById(1L);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void findById_throwsCardNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(CardNotFoundException.class,
                () -> cardService.findById(1L)
        );
    }

    @Test
    void findAll_returnsPage() {
        Page<Card> expected = new PageImpl<>(Collections.emptyList());
        when(cardRepository.findAll(any(Pageable.class))).thenReturn(expected);

        Page<Card> result = cardService.findAll(PageRequest.of(0, 10));
        Assertions.assertEquals(expected, result);
    }

    @Test
    void delete_success() {
        when(cardRepository.existsById(1L)).thenReturn(true);
        cardService.delete(1L);
        Mockito.verify(cardRepository).deleteById(1L);
    }

    @Test
    void delete_throwsCardNotFound() {
        when(cardRepository.existsById(1L)).thenReturn(false);
        Assertions.assertThrows(CardNotFoundException.class,
                () -> cardService.delete(1L)
        );
    }

    @Test
    void block_success() {
        Card card = new Card();
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        cardService.block(1L);

        Assertions.assertEquals(CardStatus.BLOCKED, card.getStatus());
        Mockito.verify(cardRepository).save(card);
    }

    @Test
    void activate_success() {
        Card card = new Card();
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        cardService.activate(1L);

        Assertions.assertEquals(CardStatus.ACTIVE, card.getStatus());
        Mockito.verify(cardRepository).save(card);
    }

    @Test
    void createCard_success() {
        CardCreateRequest request = new CardCreateRequest(1L);
        User owner = new User();
        Card expected = Card.builder()
                .owner(owner)
                .status(CardStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(cardRepository.save(any(Card.class))).thenReturn(expected);

        Card result = cardService.createCard(request);

        Assertions.assertEquals(owner, result.getOwner());
        Assertions.assertEquals(CardStatus.ACTIVE, result.getStatus());
        Assertions.assertEquals(BigDecimal.ZERO, result.getBalance());
    }

    @Test
    void deposit_success() {
        User owner = new User();
        owner.setId(1L);

        Card card = new Card();
        card.setOwner(owner);
        card.setBalance(BigDecimal.valueOf(500));
        card.setStatus(CardStatus.ACTIVE);

        User user = new User();
        user.setId(1L);

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Mockito.when(cardRepository.save(card)).thenReturn(card);

        DepositResponse response = cardService.deposit(1L, "user", BigDecimal.valueOf(100));

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(BigDecimal.valueOf(600), response.getNewBalance());
    }

    @Test
    void deposit_throwsCardInactive() {
        User owner = new User();
        owner.setId(1L);

        Card card = new Card();
        card.setOwner(owner);
        card.setStatus(CardStatus.BLOCKED);

        User user = new User();
        user.setId(1L);

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        Assertions.assertThrows(CardInactiveException.class,
                () -> cardService.deposit(1L, "user", BigDecimal.TEN)
        );
    }

    @Test
    void deposit_throwsAccessDenied() {
        Card card = new Card();
        card.setOwner(new User());
        card.getOwner().setId(1L);
        User user = new User();
        user.setId(2L);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        AccessDeniedException exception = Assertions.assertThrows(
                AccessDeniedException.class,
                () -> cardService.deposit(1L, "user", BigDecimal.TEN)
        );

        Assertions.assertEquals(Messages.ACCESS_DENIED_DEPOSIT.getMessage(), exception.getMessage());
    }

    @Test
    void findByOwner_returnsPage() {
        User owner = new User();
        Page<Card> expected = new PageImpl<>(Collections.emptyList());
        when(cardRepository.findByOwner(owner, PageRequest.of(0, 10))).thenReturn(expected);

        Page<Card> result = cardService.findByOwner(owner, PageRequest.of(0, 10));
        Assertions.assertEquals(expected, result);
    }

    @Test
    void updateCard_success() {
        CardUpdateRequest request = new CardUpdateRequest(LocalDate.now().plusYears(1), "ACTIVE");
        Card card = new Card();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.updateCard(1L, request);

        Assertions.assertEquals(request.getExpirationDate(), result.getExpirationDate());
        Assertions.assertEquals(CardStatus.ACTIVE, result.getStatus());
    }

    @Test
    void updateCard_throwsCardNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(CardNotFoundException.class,
                () -> cardService.updateCard(1L, new CardUpdateRequest())
        );
    }

    @Test
    void getBalanceResponse_success() {
        User owner = new User();
        owner.setId(1L);

        Card card = new Card();
        card.setOwner(owner);
        card.setBalance(BigDecimal.valueOf(1000));

        User user = new User();
        user.setId(1L);

        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        Mockito.when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        BalanceResponse response = cardService.getBalanceResponse(1L, "user");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(BigDecimal.valueOf(1000), response.getBalance());
    }

    @Test
    void getBalanceResponse_throwsAccessDenied() {
        Card card = new Card();
        card.setOwner(new User());
        card.getOwner().setId(1L);
        User user = new User();
        user.setId(2L);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        AccessDeniedException exception = Assertions.assertThrows(
                AccessDeniedException.class,
                () -> cardService.getBalanceResponse(1L, "user")
        );

        Assertions.assertEquals(Messages.ACCESS_DENIED_BALANCE.getMessage(), exception.getMessage());
    }
}