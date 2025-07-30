package com.example.bankcards.service.impl;

import com.example.bankcards.entity.BlockOrder;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.BlockOrderStatus;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.DuplicateBlockRequestException;
import com.example.bankcards.exception.InvalidCardOwnerException;
import com.example.bankcards.exception.RequestNotFoundException;
import com.example.bankcards.repository.BlockOrderRepository;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.BlockOrderService;
import com.example.bankcards.util.Messages;
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
