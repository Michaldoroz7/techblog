package com.doroz.article_service.controller;

import com.doroz.article_service.model.ArticleRequest;
import com.doroz.article_service.model.ArticleResponse;
import com.doroz.article_service.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest articleRequest, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.of(articleService.createArticle(articleRequest, username));
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.of(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.of(articleService.getArticleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.deleteArticle(id));
    }

    @GetMapping("/author")
    public ResponseEntity<List<ArticleResponse>> getArticlesByAuthor(Principal principal) {
        String username = principal.getName();
        List<ArticleResponse> articles = articleService.getArticlesByAuthor(username);
        return ResponseEntity.ok(articles);
    }

}
