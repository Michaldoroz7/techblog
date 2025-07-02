package com.doroz.comment_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotNull Long articleId,
        @NotBlank String content
) {}
