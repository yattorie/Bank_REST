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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class BlockOrderServiceImplTest {

    @Mock
    private BlockOrderRepository blockOrderRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private BlockOrderServiceImpl blockOrderService;

    @Test
    void createBlockRequest_success() {
        Card card = new Card();
        User user = new User();
        card.setOwner(user);

        Mockito.when(blockOrderRepository.existsByCardAndRequestedByAndStatus(
                card, user, BlockOrderStatus.PENDING
        )).thenReturn(false);

        BlockOrder expected = BlockOrder.builder()
                .card(card)
                .requestedBy(user)
                .status(BlockOrderStatus.PENDING)
                .build();

        Mockito.when(blockOrderRepository.save(any(BlockOrder.class)))
                .thenReturn(expected);

        BlockOrder result = blockOrderService.createBlockRequest(card, user);

        Assertions.assertEquals(BlockOrderStatus.PENDING, result.getStatus());
        Mockito.verify(blockOrderRepository).save(any(BlockOrder.class));
    }

    @Test
    void createBlockRequest_throwsInvalidCardOwner() {
        Card card = new Card();
        User cardOwner = new User();
        cardOwner.setId(1L);
        card.setOwner(cardOwner);

        User requester = new User();
        requester.setId(2L);

        Assertions.assertThrows(InvalidCardOwnerException.class,
                () -> blockOrderService.createBlockRequest(card, requester)
        );
        Mockito.verify(blockOrderRepository, never()).save(any());
    }

    @Test
    void createBlockRequest_throwsDuplicateRequest() {
        Card card = new Card();
        User user = new User();
        card.setOwner(user);

        Mockito.when(blockOrderRepository.existsByCardAndRequestedByAndStatus(
                card, user, BlockOrderStatus.PENDING
        )).thenReturn(true);

        Assertions.assertThrows(DuplicateBlockRequestException.class,
                () -> blockOrderService.createBlockRequest(card, user)
        );
        Mockito.verify(blockOrderRepository, never()).save(any());
    }

    @Test
    void getPendingRequests_returnsList() {
        List<BlockOrder> expected = Collections.singletonList(new BlockOrder());
        Mockito.when(blockOrderRepository.findByStatus(BlockOrderStatus.PENDING))
                .thenReturn(expected);

        List<BlockOrder> result = blockOrderService.getPendingRequests();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void approveRequest_success() {
        Long id = 1L;
        BlockOrder request = new BlockOrder();
        Card card = new Card();
        request.setCard(card);

        Mockito.when(blockOrderRepository.findById(id))
                .thenReturn(Optional.of(request));

        Mockito.when(cardRepository.save(card)).thenReturn(card);

        blockOrderService.approveRequest(id);

        Assertions.assertEquals(BlockOrderStatus.APPROVED, request.getStatus());
        Assertions.assertEquals(CardStatus.BLOCKED, card.getStatus());
        Mockito.verify(cardRepository).save(card);
        Mockito.verify(blockOrderRepository).save(request);
    }

    @Test
    void approveRequest_throwsNotFound() {
        Long id = 1L;
        Mockito.when(blockOrderRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RequestNotFoundException.class,
                () -> blockOrderService.approveRequest(id)
        );
        Mockito.verify(cardRepository, never()).save(any());
        Mockito.verify(blockOrderRepository, never()).save(any());
    }

    @Test
    void rejectRequest_success() {
        Long id = 1L;
        BlockOrder request = new BlockOrder();

        Mockito.when(blockOrderRepository.findById(id))
                .thenReturn(Optional.of(request));

        blockOrderService.rejectRequest(id);

        Assertions.assertEquals(BlockOrderStatus.REJECTED, request.getStatus());
        Mockito.verify(blockOrderRepository).save(request);
    }

    @Test
    void rejectRequest_throwsNotFound() {
        Long id = 1L;
        Mockito.when(blockOrderRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(RequestNotFoundException.class,
                () -> blockOrderService.rejectRequest(id)
        );
        Mockito.verify(blockOrderRepository, never()).save(any());
    }
}