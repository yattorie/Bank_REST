package com.orlovandrei.bank_rest.util;

import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.enums.CardStatus;
import com.orlovandrei.bank_rest.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CardExpirationScheduler {

    private final CardRepository cardRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void checkCardExpirations() {
        LocalDate today = LocalDate.now();
        List<Card> activeCards = cardRepository.findByStatusAndExpirationDateBefore(
                CardStatus.ACTIVE,
                today
        );

        activeCards.forEach(card -> card.setStatus(CardStatus.EXPIRED));
        cardRepository.saveAll(activeCards);
    }
}
