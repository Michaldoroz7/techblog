package com.doroz.article_service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {

    @NotNull(message = "Title is required")
    @Size(max = 50, message = "Title can't be longer than 50 chars")
    private String title;

    @NotNull(message = "Summary is required")
    @Size(max = 150, message = "Summary can't be longer than 150 chars")
    private String summary;

    @NotNull(message = "Content is required")
    private String content;

    @NotNull(message = "Category is required")
    private String category;

    private Instant createdAt;

}
