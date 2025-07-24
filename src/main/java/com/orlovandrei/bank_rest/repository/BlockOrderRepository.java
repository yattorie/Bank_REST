package com.orlovandrei.bank_rest.repository;

import com.orlovandrei.bank_rest.entity.BlockOrder;
import com.orlovandrei.bank_rest.entity.Card;
import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.entity.enums.BlockOrderStatus;
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
