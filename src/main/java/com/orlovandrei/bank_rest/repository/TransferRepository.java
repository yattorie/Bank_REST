package com.orlovandrei.bank_rest.repository;

import com.orlovandrei.bank_rest.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
