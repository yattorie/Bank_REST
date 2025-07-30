package com.example.bankcards.service.impl;

import com.example.bankcards.dto.balance.BalanceResponse;
import com.example.bankcards.dto.card.CardCreateRequest;
import com.example.bankcards.dto.card.CardUpdateRequest;
import com.example.bankcards.dto.deposit.DepositResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.CardInactiveException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardNumberGenerator;
import com.example.bankcards.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Card findById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND.getMessage() + id));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new CardNotFoundException(Messages.CARD_NOT_FOUND.getMessage() + id);
        }
        cardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void block(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND.getMessage() + id));
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
    }

    @Override
    @Transactional
    public void activate(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND.getMessage() + id));
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
    }

    @Override
    @Transactional
    public Card createCard(CardCreateRequest request) {
        User user = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND.getMessage()));

        String cardNumber = CardNumberGenerator.generate();

        Card card = Card.builder()
                .number(cardNumber)
                .owner(user)
                .expirationDate(LocalDate.now().plusYears(3))
                .balance(BigDecimal.ZERO)
                .status(CardStatus.ACTIVE)
                .build();

        return cardRepository.save(card);
    }

    @Override
    @Transactional
    public DepositResponse deposit(Long id, String username, BigDecimal amount) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND.getMessage() + id));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND.getMessage() + username));

        if (!card.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException(Messages.ACCESS_DENIED_DEPOSIT.getMessage());
        }

        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardInactiveException(Messages.CARD_INACTIVE.getMessage());
        }

        BigDecimal newBalance = card.getBalance().add(amount);
        card.setBalance(newBalance);
        cardRepository.save(card);

        return new DepositResponse(
                true,
                Messages.DEPOSIT_SUCCESS.getMessage(),
                newBalance
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Card> findByOwner(User owner, Pageable pageable) {
        return cardRepository.findByOwner(owner, pageable);
    }

    @Override
    @Transactional
    public Card updateCard(Long id, CardUpdateRequest request) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND.getMessage() + id));

        if (request.getExpirationDate() != null) {
            card.setExpirationDate(request.getExpirationDate());
        }
        if (request.getStatus() != null) {
            try {
                CardStatus newStatus = CardStatus.valueOf(request.getStatus().toUpperCase());
                card.setStatus(newStatus);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(Messages.INVALID_STATUS_VALUE.getMessage() + request.getStatus());
            }
        }
        return cardRepository.save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public BalanceResponse getBalanceResponse(Long id, String username) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND.getMessage() + id));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_ID.getMessage() + username));

        if (!card.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException(Messages.ACCESS_DENIED_BALANCE.getMessage());
        }

        return new BalanceResponse(
                true,
                Messages.BALANCE_RETRIEVED_SUCCESS.getMessage(),
                card.getBalance()
        );
    }
}

