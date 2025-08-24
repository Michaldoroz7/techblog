package com.doroz.auth_service.service;

import com.doroz.auth_service.event.UserActivityType;
import com.doroz.auth_service.event.UserEventProducer;
import com.doroz.auth_service.model.Role;
import com.doroz.auth_service.model.User;
import com.doroz.auth_service.model.UserRequest;
import com.doroz.auth_service.model.UserResponse;
import com.doroz.auth_service.repository.UserRepository;
import com.doroz.events.UserEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder encoder;

    private UserEventProducer producer;


    public UserService(UserRepository userRepository, PasswordEncoder encoder, UserEventProducer producer) {
        this.producer = producer;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Optional<UserResponse> registerUser(UserRequest userRequest) {
        User user = User.mapRequestToUser(userRequest);
        user.setPassword(encoder.encode(userRequest.password()));
        user.setCreatedAt(Instant.now());
        user.setRole(Role.USER);
        User saved = userRepository.save(user);


        UserEvent userActivity = new UserEvent(
                user.getUsername(), UserActivityType.CREATED.getLabel(), "Auth", saved.getId(), "User was created", Instant.now());
        producer.sendActivity(userActivity);
        return Optional.of(new UserResponse(saved.getUsername(), saved.getEmail(), saved.getCreatedAt(), saved.getRole()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getRole()))
                .toList();
    }

}
