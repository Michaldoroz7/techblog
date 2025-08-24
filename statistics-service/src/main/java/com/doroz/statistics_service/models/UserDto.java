package com.doroz.statistics_service.models;

import java.time.LocalDateTime;


public record UserDto(String username, LocalDateTime createdAt) {

}
