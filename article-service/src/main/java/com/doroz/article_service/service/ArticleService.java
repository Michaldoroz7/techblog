package com.doroz.article_service.service;

import com.doroz.article_service.model.Article;
import com.doroz.article_service.model.ArticleRequest;
import com.doroz.article_service.model.ArticleResponse;
import com.doroz.article_service.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Optional<ArticleResponse> createArticle(ArticleRequest articleRequest, String authorUsername) {
        Article article = Article.mapRequestToArticle(articleRequest);
        article.setAuthorName(authorUsername);
        article.setCreatedAt(Instant.now());
        article.setCommentIds(new ArrayList<>());
        articleRepository.save(article);
        return Optional.of(ArticleResponse.mapArticleToResponse(article));
    }

    public Optional<List<ArticleResponse>> getAllArticles() {
        return Optional.of(articleRepository.findAll().stream()
                .map(ArticleResponse::mapArticleToResponse)
                .collect(Collectors.toList()));
    }

    public Optional<ArticleResponse> getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleResponse::mapArticleToResponse);
    }

    public String deleteArticle(Long id) {
        articleRepository.deleteById(id);
        return "Article successfully deleted";
    }

    @Transactional
    public List<ArticleResponse> getArticlesByAuthor(String authorUsername) {
        List<Article> articles = articleRepository.findByAuthorName(authorUsername);
        return articles.stream()
                .map(ArticleResponse::mapArticleToResponse)
                .collect(Collectors.toList());
    }

}
