package com.orlovandrei.bank_rest.service.impl;

import com.orlovandrei.bank_rest.entity.BlockOrder;
import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.entity.enums.BlockOrderStatus;
import com.orlovandrei.bank_rest.entity.enums.CardStatus;
import com.orlovandrei.bank_rest.exception.DuplicateBlockRequestException;
import com.orlovandrei.bank_rest.exception.InvalidCardOwnerException;
import com.orlovandrei.bank_rest.exception.RequestNotFoundException;
import com.orlovandrei.bank_rest.repository.BlockOrderRepository;
import com.orlovandrei.bank_rest.repository.CardRepository;
import com.orlovandrei.bank_rest.service.BlockOrderService;
import com.orlovandrei.bank_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockOrderServiceImpl implements BlockOrderService {

    private final BlockOrderRepository blockOrderRepository;
    private final CardRepository cardRepository;

    @Override
    @Transactional
    public BlockOrder createBlockRequest(Card card, User requestedBy) {
        if (!card.getOwner().equals(requestedBy)) {
            throw new InvalidCardOwnerException(Messages.INVALID_CARD_OWNER.getMessage());
        }
        boolean hasPendingRequest = blockOrderRepository.existsByCardAndRequestedByAndStatus(
                card,
                requestedBy,
                BlockOrderStatus.PENDING
        );

        if (hasPendingRequest) {
            throw new DuplicateBlockRequestException(Messages.CARD_BLOCK_REQUEST_EXISTS.getMessage());
        }

        BlockOrder request = BlockOrder.builder()
                .card(card)
                .requestedBy(requestedBy)
                .status(BlockOrderStatus.PENDING)
                .build();
        return blockOrderRepository.save(request);
    }

    @Override
    public List<BlockOrder> getPendingRequests() {
        return blockOrderRepository.findByStatus(BlockOrderStatus.PENDING);
    }

    @Override
    @Transactional
    public void approveRequest(Long id) {
        BlockOrder request = blockOrderRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(Messages.REQUEST_NOT_FOUND.getMessage()));
        Card card = request.getCard();
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        request.setStatus(BlockOrderStatus.APPROVED);
        blockOrderRepository.save(request);
    }

    @Override
    @Transactional
    public void rejectRequest(Long id) {
        BlockOrder request = blockOrderRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(Messages.REQUEST_NOT_FOUND.getMessage()));
        request.setStatus(BlockOrderStatus.REJECTED);
        blockOrderRepository.save(request);
    }
}
