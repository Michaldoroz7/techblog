package com.doroz.article_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {

    private Long id;

    private String title;

    private String summary;

    private String content;

    private String category;

    private String authorName;

    private Instant createdAt;

    private List<Long> commentsIds;

    public static ArticleResponse mapArticleToResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .content(article.getContent())
                .category(article.getCategory())
                .authorName(article.getAuthorName())
                .createdAt(article.getCreatedAt())
                .commentsIds(article.getCommentIds())
                .build();
    }
}
