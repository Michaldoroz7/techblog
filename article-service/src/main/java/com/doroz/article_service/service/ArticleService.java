package com.doroz.article_service.service;

import com.doroz.article_service.event.ArticleActivityType;
import com.doroz.article_service.event.ArticleEventProducer;
import com.doroz.article_service.model.Article;
import com.doroz.article_service.model.ArticleRequest;
import com.doroz.article_service.model.ArticleResponse;
import com.doroz.article_service.repository.ArticleRepository;
import com.doroz.events.ActivityEvent;
import com.doroz.events.ArticleViewsEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleEventProducer producer;

    public ArticleService(ArticleRepository articleRepository, ArticleEventProducer producer) {
        this.articleRepository = articleRepository;
        this.producer = producer;
    }

    public Optional<ArticleResponse> createArticle(ArticleRequest articleRequest, String authorUsername) {
        Article article = Article.mapRequestToArticle(articleRequest);
        article.setAuthorName(authorUsername);
        article.setCreatedAt(Instant.now());
        article.setCommentIds(new ArrayList<>());
        article.setViews(0L);
        Article saved = articleRepository.save(article);

        ActivityEvent activityEvent = new ActivityEvent(
                authorUsername, ArticleActivityType.CREATED.getLabel(), "Article", saved.getId(), "Article was created", Instant.now());
        producer.sendActivity(activityEvent);

        return Optional.of(new ArticleResponse(saved.getId(), saved.getTitle(), saved.getSummary(), saved.getContent(), saved.getCategory(), saved.getAuthorName(), saved.getCreatedAt(), saved.getCommentIds(), saved.getViews()));
    }

    public Optional<List<ArticleResponse>> getAllArticles() {
        return Optional.of(articleRepository.findAll().stream()
                .map(article -> new ArticleResponse(article.getId(), article.getTitle(), article.getSummary(), article.getContent(), article.getCategory(), article.getAuthorName(), article.getCreatedAt(), article.getCommentIds(), article.getViews()))
                .collect(Collectors.toList()));
    }

    public Optional<ArticleResponse> getArticleById(Long id) {
        ArticleViewsEvent event = ArticleViewsEvent.builder()
                .articleId(id)
                .build();

        producer.sendViews(event);

        Optional<Article> articleResponse = articleRepository.findById(id);

        return articleResponse.map(article -> new ArticleResponse(article.getId(), article.getTitle(), article.getSummary(), article.getContent(), article.getCategory(), article.getAuthorName(), article.getCreatedAt(), article.getCommentIds(), article.getViews()));

    }

    public String deleteArticle(Long id) {
        articleRepository.deleteById(id);
        return "Article successfully deleted";
    }

    @Transactional
    public List<ArticleResponse> getArticlesByAuthor(String authorUsername) {
        List<Article> articles = articleRepository.findByAuthorName(authorUsername);
        return articles.stream()
                .map(article -> new ArticleResponse(article.getId(), article.getTitle(), article.getSummary(), article.getContent(), article.getCategory(), article.getAuthorName(), article.getCreatedAt(), article.getCommentIds(), article.getViews()))
                .collect(Collectors.toList());
    }

}
