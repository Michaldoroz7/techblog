package com.doroz.auth_service.service;

import com.doroz.auth_service.model.Role;
import com.doroz.auth_service.model.User;
import com.doroz.auth_service.model.UserRequest;
import com.doroz.auth_service.model.UserResponse;
import com.doroz.auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder encoder;


    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public Optional<UserResponse> registerUser(UserRequest userRequest) {
        User user = User.mapRequestToUser(userRequest);
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setRole(Role.USER);
        userRepository.save(user);
        return Optional.of(UserResponse.mapUserToResponse(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
