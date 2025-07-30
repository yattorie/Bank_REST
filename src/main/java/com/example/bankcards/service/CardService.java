package com.example.bankcards.service;

import com.example.bankcards.dto.balance.BalanceResponse;
import com.example.bankcards.dto.card.CardCreateRequest;
import com.example.bankcards.dto.card.CardUpdateRequest;
import com.example.bankcards.dto.deposit.DepositResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface CardService {
    Card findById(Long id);

    @Transactional(readOnly = true)
    Page<Card> findAll(Pageable pageable);

    void delete(Long id);

    @Transactional
    void block(Long id);

    @Transactional
    void activate(Long id);

    @Transactional
    Card createCard(CardCreateRequest request);

    @Transactional
    DepositResponse deposit(Long id, String username, BigDecimal amount);

    @Transactional(readOnly = true)
    Page<Card> findByOwner(User owner, Pageable pageable);

    @Transactional
    Card updateCard(Long id, CardUpdateRequest request);

    @Transactional(readOnly = true)
    BalanceResponse getBalanceResponse(Long id, String username);
}
