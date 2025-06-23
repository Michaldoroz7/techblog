package com.doroz.auth_service.controller;

import com.doroz.auth_service.jwt.JwtResponse;
import com.doroz.auth_service.jwt.JwtUtils;
import com.doroz.auth_service.model.LoginRequest;
import com.doroz.auth_service.model.UserRequest;
import com.doroz.auth_service.model.UserResponse;
import com.doroz.auth_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
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
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtUtils.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
