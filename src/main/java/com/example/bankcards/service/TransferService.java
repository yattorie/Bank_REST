package com.example.bankcards.service;

import com.example.bankcards.dto.transfer.TransferRequest;
import com.example.bankcards.dto.transfer.TransferResponse;
import com.example.bankcards.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface TransferService {
    @Transactional
    TransferResponse transferBetweenOwnCards(User user, TransferRequest requestDto);
}
