package com.example.bankcards.controller;

import com.example.bankcards.dto.blockorder.BlockOrderRequest;
import com.example.bankcards.dto.mapper.BlockOrderMapper;
import com.example.bankcards.dto.success.SuccessResponse;
import com.example.bankcards.service.BlockOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BlockOrderControllerTest {

    @Mock
    private BlockOrderService blockOrderService;

    @Mock
    private BlockOrderMapper blockOrderMapper;

    @InjectMocks
    private BlockOrderController blockOrderController;

    @Test
    void getPendingRequests_returnsPendingRequests() {
        BlockOrderRequest request = new BlockOrderRequest();
        List<BlockOrderRequest> expected = Collections.singletonList(request);

        Mockito.when(blockOrderService.getPendingRequests())
                .thenReturn(Collections.emptyList());
        Mockito.when(blockOrderMapper.toDto(Collections.emptyList()))
                .thenReturn(expected);

        ResponseEntity<List<BlockOrderRequest>> response =
                blockOrderController.getPendingRequests();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expected, response.getBody());
        Mockito.verify(blockOrderService).getPendingRequests();
    }

    @Test
    void approve_returnsSuccessResponse() {
        Long id = 1L;
        Mockito.doNothing().when(blockOrderService).approveRequest(id);

        ResponseEntity<SuccessResponse> response =
                blockOrderController.approve(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Request approved and card blocked",
                response.getBody().getMessage());
        Mockito.verify(blockOrderService).approveRequest(id);
    }

    @Test
    void reject_returnsSuccessResponse() {
        Long id = 1L;
        Mockito.doNothing().when(blockOrderService).rejectRequest(id);

        ResponseEntity<SuccessResponse> response =
                blockOrderController.reject(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Request rejected",
                response.getBody().getMessage());
        Mockito.verify(blockOrderService).rejectRequest(id);
    }
}