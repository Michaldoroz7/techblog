package com.doroz.auth_service;

import com.doroz.auth_service.event.UserEventProducer;
import com.doroz.auth_service.model.Role;
import com.doroz.auth_service.model.User;
import com.doroz.auth_service.model.UserRequest;
import com.doroz.auth_service.model.UserResponse;
import com.doroz.auth_service.repository.UserRepository;
import com.doroz.auth_service.service.UserService;
import com.doroz.events.UserEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTests {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserEventProducer producer;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserEvent userEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUser() {

        // given
        UserRequest userRequest = new UserRequest("michaldoroz", "email@email.com", "password", Instant.now(), Role.USER);

        User user = User.mapRequestToUser(userRequest);

        when(repository.existsByUsername(userRequest.username())).thenReturn(false);
        when(encoder.encode(userRequest.password())).thenReturn("encodedPassword");
        when(userDetailsService.loadUserByUsername(userRequest.username())).thenReturn(null);
        when(repository.save(any(User.class))).thenReturn(user);

        // when
        Optional<UserResponse> result = userService.registerUser(userRequest);

        // then
        assertTrue(result.isPresent());
        assertEquals("michaldoroz", result.get().username());
        assertEquals("email@email.com", result.get().email());
    }

    @Test
    void shouldLoadUserByUsername() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("michaldoroz");
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setCreatedAt(Instant.now());
        when(repository.findByUsername("michaldoroz")).thenReturn(Optional.of(user));

        // when
        Optional<User> result = userService.getByUsername("michaldoroz");

        // then
        assertTrue(result.isPresent());
        assertEquals("michaldoroz", result.get().getUsername());
        assertEquals("email@email.com", result.get().getEmail());
    }

    @Test
    void shouldGetAllUsers() {
        // given
        List<User> users = generateUsers();
        when(repository.findAll()).thenReturn(users);

        // when
        List<UserResponse> result = userService.getAllUsers();

        // then
        assertTrue(result.getFirst().username().equals("michaldoroz"));
        assertEquals(1, result.size());
    }

    @Test
    void shouldGetByUsername() {
        // given
        List<User> users = generateUsers();
        when(repository.findByUsername("michaldoroz")).thenReturn(Optional.of(users.get(0)));

        // when
        Optional<User> result = userService.getByUsername("michaldoroz");

        // then
        assertTrue(result.isPresent());
        assertEquals("michaldoroz", result.get().getUsername());
    }

    private List<User> generateUsers() {
        User user = new User();
        user.setId(1L);
        user.setUsername("michaldoroz");
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setCreatedAt(Instant.now());
        return List.of(user);
    }
}
