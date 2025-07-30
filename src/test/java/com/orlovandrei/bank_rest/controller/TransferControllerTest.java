package com.orlovandrei.bank_rest.controller;

import com.orlovandrei.bank_rest.dto.transfer.TransferRequest;
import com.orlovandrei.bank_rest.dto.transfer.TransferResponse;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.service.TransferService;
import com.orlovandrei.bank_rest.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransferController transferController;

    @Test
    void transfer_success_returnsTransferResponse() {
        UserDetails principal = Mockito.mock(UserDetails.class);
        TransferRequest request = new TransferRequest(1L, 2L, new BigDecimal("50.00"));
        User user = User.builder().id(1L).username("testuser").build();
        TransferResponse response = new TransferResponse(true, "Transfer successful", LocalDateTime.now());

        Mockito.when(principal.getUsername()).thenReturn("testuser");
        Mockito.when(userService.getByUsername("testuser")).thenReturn(user);
        Mockito.when(transferService.transferBetweenOwnCards(user, request)).thenReturn(response);

        ResponseEntity<TransferResponse> result = transferController.transfer(principal, request);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(response, result.getBody());
        Mockito.verify(userService).getByUsername("testuser");
        Mockito.verify(transferService).transferBetweenOwnCards(user, request);
    }
}
