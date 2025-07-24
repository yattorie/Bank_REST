package com.orlovandrei.bank_rest.service;

import com.orlovandrei.bank_rest.dto.balance.BalanceResponse;
import com.orlovandrei.bank_rest.dto.card.CardRequest;
import com.orlovandrei.bank_rest.dto.card.CardUpdateRequest;
import com.orlovandrei.bank_rest.dto.deposit.DepositResponse;
import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.User;
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
    Card createCard(CardRequest request);

    @Transactional
    DepositResponse deposit(Long id, String username, BigDecimal amount);

    @Transactional(readOnly = true)
    Page<Card> findByOwner(User owner, Pageable pageable);

    @Transactional
    Card updateCard(Long id, CardUpdateRequest request);

    @Transactional(readOnly = true)
    BalanceResponse getBalanceResponse(Long id, String username);
}
