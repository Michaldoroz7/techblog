package com.doroz.comment_service.model;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Long articleId,
        String content,
        String authorUsername,
        LocalDateTime createdAt
) {
}
