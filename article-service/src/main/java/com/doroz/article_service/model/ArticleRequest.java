package com.doroz.article_service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record ArticleRequest(

        @NotNull(message = "Author name is required")
        @Size(max = 50, message = "Author name can't be longer than 50 chars")
        String authorName,

        @NotNull(message = "Title is required")
        @Size(max = 50, message = "Title can't be longer than 50 chars")
        String title,

        @NotNull(message = "Summary is required")
        @Size(max = 150, message = "Summary can't be longer than 150 chars")
        String summary,

        @NotNull(message = "Content is required")
        String content,

        String category,

        Instant createdAt) {


}
