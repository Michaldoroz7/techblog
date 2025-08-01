package com.doroz.statistics_service.client;

import com.doroz.statistics_service.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "auth-service")
public interface UserClient {
    @GetMapping("/api/auth")
    List<UserDto> findAll();
}

