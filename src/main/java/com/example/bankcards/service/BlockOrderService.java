package com.example.bankcards.service;


import com.example.bankcards.entity.BlockOrder;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;

import java.util.List;

public interface BlockOrderService {
    BlockOrder createBlockRequest(Card card, User requestedBy);
    List<BlockOrder> getPendingRequests();
    void approveRequest(Long requestId);
    void rejectRequest(Long requestId);
}
