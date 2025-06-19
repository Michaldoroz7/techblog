package com.doroz.auth_service.jwt;

import lombok.Getter;

@Getter
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
