package com.doroz.auth_service.controller;

import com.doroz.auth_service.jwt.JwtResponse;
import com.doroz.auth_service.jwt.JwtUtils;
import com.doroz.auth_service.model.LoginRequest;
import com.doroz.auth_service.model.UserRequest;
import com.doroz.auth_service.model.UserResponse;
import com.doroz.auth_service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class UserController {

    private UserService userService;

    private JwtUtils jwtUtils;

    private AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.of(userService.registerUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtUtils.generateToken(user);

        return ResponseEntity.ok()
                .header("X-Username", user.getUsername())
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "X-Username")
                .body(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        log.info("Użytkownik wywołał /me jako: {}", authentication.getName());

        return ResponseEntity.of(userService.getByUsername(authentication.getName())
                .map(user -> new UserResponse(
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getRole()
                )));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
