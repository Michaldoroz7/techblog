package com.doroz.api_gateway.configuration;

import com.doroz.api_gateway.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers("/auth-service/api/auth/register").permitAll()
                        .pathMatchers("/auth-service/api/auth/login").permitAll()

                        .pathMatchers(HttpMethod.GET, "/article-service/api/articles").permitAll()

                        .pathMatchers(HttpMethod.POST, "/comment-service/api/comments").authenticated()
                        .pathMatchers(HttpMethod.GET, "/comment-service/api/comments/**").permitAll()


                        .anyExchange().authenticated()
                )
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}

