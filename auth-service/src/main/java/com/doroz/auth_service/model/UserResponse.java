package com.doroz.auth_service.model;

import java.time.Instant;

public record UserResponse(String username, String email, Instant createdAt, Role role) {
}
