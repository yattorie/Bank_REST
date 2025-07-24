package com.orlovandrei.bank_rest.repository;

import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.entity.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CardRepository extends JpaRepository <Card, Long> {
    Page<Card> findByOwner(User owner, Pageable pageable);

    List<Card> findByStatusAndExpirationDateBefore(
            CardStatus cardStatus,
            LocalDate expirationDate);
}
