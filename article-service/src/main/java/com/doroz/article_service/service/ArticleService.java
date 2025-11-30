package com.doroz.article_service.service;

import com.doroz.article_service.event.ArticleActivityType;
import com.doroz.article_service.event.ArticleEventProducer;
import com.doroz.article_service.model.Article;
import com.doroz.article_service.model.ArticleRequest;
import com.doroz.article_service.model.ArticleResponse;
import com.doroz.article_service.repository.ArticleRepository;
import com.doroz.events.ActivityEvent;
import com.doroz.events.ArticleViewsEvent;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleEventProducer producer;

    public ArticleService(ArticleRepository articleRepository, ArticleEventProducer producer) {
        this.articleRepository = articleRepository;
        this.producer = producer;
    }

    @Transactional
    public ArticleResponse createArticle(ArticleRequest articleRequest, String authorUsername) {
        Article article = Article.mapRequestToArticle(articleRequest);
        article.setAuthorName(authorUsername);
        article.setCreatedAt(Instant.now());
        article.setCommentIds(new ArrayList<>());
        article.setViews(0L);

        Article saved = articleRepository.save(article);

        ActivityEvent activityEvent = new ActivityEvent(
                authorUsername,
                ArticleActivityType.CREATED.getLabel(),
                "Article",
                saved.getId(),
                "Article was created",
                Instant.now()
        );
        producer.sendActivity(activityEvent);

        log.info("Article created id={} by={}", saved.getId(), authorUsername);
        return toResponse(saved);
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + id));

        ArticleViewsEvent event = ArticleViewsEvent.builder()
                .articleId(id)
                .build();
        producer.sendViews(event);

        return toResponse(article);
    }

    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article not found with id: " + id);
        }
        articleRepository.deleteById(id);
        log.info("Article deleted id={}", id);
    }

    @Transactional
    public List<ArticleResponse> getArticlesByAuthor(String authorUsername) {
        return articleRepository.findByAuthorName(authorUsername).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ArticleResponse toResponse(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getSummary(),
                article.getContent(),
                article.getCategory(),
                article.getAuthorName(),
                article.getCreatedAt(),
                article.getCommentIds(),
                article.getViews()
        );
    }

}
