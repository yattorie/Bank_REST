package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
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
