package com.doroz.article_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {

    private String title;

    private String summary;

    private String content;

    private String category;

    private String authorName;

    private Instant createdAt;

    public static ArticleResponse mapArticleToResponse(Article article) {
        return ArticleResponse.builder()
                .title(article.getTitle())
                .summary(article.getSummary())
                .content(article.getContent())
                .category(article.getCategory())
                .authorName(article.getAuthorName())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
