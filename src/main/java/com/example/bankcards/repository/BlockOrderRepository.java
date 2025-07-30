package com.example.bankcards.repository;

import com.example.bankcards.entity.BlockOrder;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.BlockOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockOrderRepository extends JpaRepository<BlockOrder, Long> {
    List<BlockOrder> findByStatus(BlockOrderStatus blockOrderStatus);

    boolean existsByCardAndRequestedByAndStatus(
            Card card,
            User requestedBy,
            BlockOrderStatus status
    );
}
