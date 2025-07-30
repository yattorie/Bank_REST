package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional(readOnly = true)
    User getById(Long id);

    @Transactional(readOnly = true)
    User getByUsername(String username);

    @Transactional(readOnly = true)
    User getByEmail(String email);

    @Transactional(readOnly = true)
    Page<User> findAll(Pageable pageable);

    @Transactional
    User create(User user);

    @Transactional
    User update(Long id, User user);

    @Transactional
    void delete(Long id);

}
