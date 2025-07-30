package com.orlovandrei.bank_rest.service.impl;

import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.entity.enums.Role;
import com.orlovandrei.bank_rest.exception.EmailAlreadyExistsException;
import com.orlovandrei.bank_rest.exception.UserAlreadyExistsException;
import com.orlovandrei.bank_rest.exception.UserNotFoundException;
import com.orlovandrei.bank_rest.repository.UserRepository;
import com.orlovandrei.bank_rest.service.UserService;
import com.orlovandrei.bank_rest.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_ID.getMessage() + id));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_USERNAME.getMessage() + username));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_EMAIL.getMessage() + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(Messages.USERNAME.getMessage() + user.getUsername() + Messages.IS_ALREADY_TAKEN.getMessage());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(Messages.EMAIL.getMessage() + user.getEmail() + Messages.IS_ALREADY_TAKEN.getMessage());
        }
        User entity = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        return userRepository.save(entity);
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        User updatingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_ID.getMessage() + id));

        if (!updatingUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(Messages.USERNAME.getMessage() + user.getUsername() + Messages.IS_ALREADY_TAKEN.getMessage());
        }
        if (!updatingUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(Messages.EMAIL.getMessage() + user.getEmail() + Messages.IS_ALREADY_TAKEN.getMessage());
        }

        updatingUser.setUsername(user.getUsername());
        updatingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            updatingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRole() != null) {
            updatingUser.setRole(user.getRole());
        }
        return userRepository.save(updatingUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(Messages.USER_NOT_FOUND_ID.getMessage() + id);
        }
        userRepository.deleteById(id);
    }
}

