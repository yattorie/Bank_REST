package com.orlovandrei.bank_rest.service.impl;

import com.orlovandrei.bank_rest.entity.User;
import com.orlovandrei.bank_rest.entity.enums.Role;
import com.orlovandrei.bank_rest.exception.EmailAlreadyExistsException;
import com.orlovandrei.bank_rest.exception.UserAlreadyExistsException;
import com.orlovandrei.bank_rest.exception.UserNotFoundException;
import com.orlovandrei.bank_rest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getById_success() {
        User user = User.builder().id(1L).username("testuser").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getById(1L);

        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getById_throwsUserNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getById(1L));
    }

    @Test
    void getByUsername_success() {
        User user = User.builder().id(1L).username("testuser").build();
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User result = userService.getByUsername("testuser");

        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository).findByUsername("testuser");
    }

    @Test
    void getByUsername_throwsUserNotFound() {
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getByUsername("testuser"));
    }

    @Test
    void getByEmail_success() {
        User user = User.builder().id(1L).email("test@example.com").build();
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.getByEmail("test@example.com");

        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void getByEmail_throwsUserNotFound() {
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getByEmail("test@example.com"));
    }

    @Test
    void findAll() {
        Page<User> page = new PageImpl<>(List.of(new User(), new User()));
        Mockito.when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.findAll(Pageable.unpaged());

        Assertions.assertEquals(2, result.getTotalElements());
        Mockito.verify(userRepository).findAll(any(Pageable.class));
    }

    @Test
    void create_success() {
        User user = User.builder().username("newuser").email("new@example.com").password("password").build();
        Mockito.when(userRepository.existsByUsername("newuser")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.create(user);

        Assertions.assertEquals("newuser", result.getUsername());
        Assertions.assertEquals("new@example.com", result.getEmail());
        Assertions.assertEquals("encodedPassword", result.getPassword());
        Assertions.assertEquals(Role.ROLE_USER, result.getRole());
        Mockito.verify(userRepository).save(any(User.class));
    }

    @Test
    void create_throwsUserAlreadyExists() {
        User user = User.builder().username("existinguser").email("new@example.com").build();
        Mockito.when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    }

    @Test
    void create_throwsEmailAlreadyExists() {
        User user = User.builder().username("newuser").email("existing@example.com").build();
        Mockito.when(userRepository.existsByUsername("newuser")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.create(user));
    }

    @Test
    void update_success() {
        User existingUser = User.builder().id(1L).username("olduser").email("old@example.com").password("oldpassword").role(Role.ROLE_USER).build();
        User updatedUser = User.builder().username("newuser").email("new@example.com").password("newpassword").role(Role.ROLE_ADMIN).build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.existsByUsername("newuser")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.update(1L, updatedUser);

        Assertions.assertEquals("newuser", result.getUsername());
        Assertions.assertEquals("new@example.com", result.getEmail());
        Assertions.assertEquals("encodedNewPassword", result.getPassword());
        Assertions.assertEquals(Role.ROLE_ADMIN, result.getRole());
        Mockito.verify(userRepository).save(existingUser);
    }

    @Test
    void update_throwsUserAlreadyExists() {
        User existingUser = User.builder().id(1L).username("olduser").email("old@example.com").build();
        User updatedUser = User.builder().username("existinguser").email("new@example.com").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.update(1L, updatedUser));
    }

    @Test
    void update_throwsEmailAlreadyExists() {
        User existingUser = User.builder().id(1L).username("olduser").email("old@example.com").build();
        User updatedUser = User.builder().username("newuser").email("existing@example.com").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.existsByUsername("newuser")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.update(1L, updatedUser));
    }

    @Test
    void delete_success() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        userService.delete(1L);

        Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_throwsUserNotFound() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.delete(1L));
    }
}