package com.orlovandrei.bank_rest.service.impl;

import com.orlovandrei.bank_rest.dto.transfer.TransferRequest;
import com.orlovandrei.bank_rest.dto.transfer.TransferResponse;
import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.Transfer;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.entity.enums.CardStatus;
import com.orlovandrei.bank_rest.entity.enums.TransferStatus;
import com.orlovandrei.bank_rest.exception.AmountExceedsLimitException;
import com.orlovandrei.bank_rest.exception.CardInactiveException;
import com.orlovandrei.bank_rest.exception.CardNotFoundException;
import com.orlovandrei.bank_rest.exception.InsufficientFundsException;
import com.orlovandrei.bank_rest.exception.InvalidCardOwnerException;
import com.orlovandrei.bank_rest.exception.TransferBetweenSameCardException;
import com.orlovandrei.bank_rest.repository.CardRepository;
import com.orlovandrei.bank_rest.repository.TransferRepository;
import com.orlovandrei.bank_rest.service.TransferService;
import com.orlovandrei.bank_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;

    @Override
    @Transactional
    public TransferResponse transferBetweenOwnCards(User user, TransferRequest requestDto) {
        if (requestDto.getFromCardId().equals(requestDto.getToCardId())) {
            throw new TransferBetweenSameCardException(Messages.SAME_CARD_TRANSFER.getMessage());
        }

        Card from = cardRepository.findById(requestDto.getFromCardId())
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND_TRANSFER.getMessage()));
        if (!from.getOwner().getId().equals(user.getId())) {
            throw new InvalidCardOwnerException(Messages.INVALID_CARD_OWNER.getMessage());
        }

        Card to = cardRepository.findById(requestDto.getToCardId())
                .orElseThrow(() -> new CardNotFoundException(Messages.CARD_NOT_FOUND_TRANSFER.getMessage()));
        if (!to.getOwner().getId().equals(user.getId())) {
            throw new InvalidCardOwnerException(Messages.INVALID_CARD_OWNER.getMessage());
        }

        if (from.getStatus() != CardStatus.ACTIVE || to.getStatus() != CardStatus.ACTIVE) {
            throw new CardInactiveException(Messages.CARD_INACTIVE.getMessage());
        }

        BigDecimal amount = requestDto.getAmount();
        if (amount.compareTo(BigDecimal.valueOf(10000)) > 0) {
            throw new AmountExceedsLimitException(Messages.AMOUNT_EXCEEDS_LIMIT.getMessage());
        }
        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(Messages.INSUFFICIENT_FUNDS.getMessage());
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        cardRepository.save(from);
        cardRepository.save(to);

        Transfer transfer = Transfer.builder()
                .fromCard(from)
                .toCard(to)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .status(TransferStatus.SUCCESS)
                .build();
        transferRepository.save(transfer);

        return new TransferResponse(true,
                Messages.TRANSFER_SUCCESS.getMessage(),
                transfer.getTimestamp());
    }
}
