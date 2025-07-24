package com.orlovandrei.bank_rest.service;

import com.orlovandrei.bank_rest.dto.transfer.TransferRequest;
import com.orlovandrei.bank_rest.dto.transfer.TransferResponse;
import com.orlovandrei.bank_rest.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface TransferService {
    @Transactional
    TransferResponse transferBetweenOwnCards(User user, TransferRequest requestDto);
}
