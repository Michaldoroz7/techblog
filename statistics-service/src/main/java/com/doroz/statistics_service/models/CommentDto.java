package com.doroz.statistics_service.models;

import java.time.LocalDateTime;


public record CommentDto(Long id, String content, LocalDateTime createdAt) {
}
