package com.orlovandrei.bank_rest.service;


import com.orlovandrei.bank_rest.entity.BlockOrder;
import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.User;

import java.util.List;

public interface BlockOrderService {
    BlockOrder createBlockRequest(Card card, User requestedBy);
    List<BlockOrder> getPendingRequests();
    void approveRequest(Long requestId);
    void rejectRequest(Long requestId);
}
