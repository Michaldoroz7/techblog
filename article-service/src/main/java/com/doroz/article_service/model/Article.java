package com.doroz.article_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String summary;

    @Lob
    private String content;

    private String category;

    private String authorName;

    private Instant createdAt;

    @ElementCollection
    private List<Long> commentIds = new ArrayList<>();

    public static Article mapRequestToArticle(ArticleRequest request) {
        return Article.builder()
                .title(request.getTitle())
                .summary(request.getSummary())
                .content(request.getContent())
                .category(request.getCategory())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
