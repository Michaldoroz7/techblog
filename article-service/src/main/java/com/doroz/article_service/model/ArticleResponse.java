package com.doroz.article_service.model;

import java.time.Instant;
import java.util.List;

public record ArticleResponse(Long id, String title, String summary, String content, String category, String authorName,
                              Instant createdAt, List<Long> commentsIds, Long views) {
}
