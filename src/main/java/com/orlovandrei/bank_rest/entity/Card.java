package com.orlovandrei.bank_rest.entity;

import com.orlovandrei.bank_rest.entity.enums.CardStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "number", nullable = false, length = 255)
    @Convert(converter = CardNumberConverter.class)
    String number;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    User owner;

    @Column(name = "expiration_date", nullable = false)
    LocalDate expirationDate;

    @Column(name = "balance", nullable = false)
    BigDecimal balance;

    @Enumerated(EnumType.STRING)
    CardStatus status;

    @OneToMany(mappedBy = "fromCard", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transfer> outgoingTransfers;

    @OneToMany(mappedBy = "toCard", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transfer> incomingTransfers;
}
