package com.doroz.article_service.service;

import com.doroz.article_service.event.ArticleEventProducer;
import com.doroz.article_service.model.Article;
import com.doroz.article_service.model.ArticleRequest;
import com.doroz.article_service.model.ArticleResponse;
import com.doroz.article_service.repository.ArticleRepository;
import com.doroz.events.ActivityEvent;
import com.doroz.events.ArticleViewsEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ArticleServiceTests {

    @Mock
    private ArticleRepository repository;

    @Mock
    private ArticleEventProducer producer;

    @InjectMocks
    private ArticleService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateArticle() {
        // given
        ArticleRequest request = new ArticleRequest();
        request.setTitle("Test title");
        request.setContent("Test content");

        Article savedArticle = Article.mapRequestToArticle(request);
        savedArticle.setId(1L);
        savedArticle.setCreatedAt(Instant.now());
        savedArticle.setViews(0L);
        savedArticle.setCommentIds(new ArrayList<>());
        savedArticle.setAuthorName("michaldoroz");

        when(repository.save(any(Article.class))).thenReturn(savedArticle);

        // when
        Optional<ArticleResponse> result = service.createArticle(request, "michaldoroz");

        // then
        assertTrue(result.isPresent());
        assertEquals("Test title", result.get().getTitle());
        verify(producer, times(1)).sendActivity(any(ActivityEvent.class));
    }

    @Test
    void shouldReturnAllArticles() {
        // given
        List<Article> articles = generateArticles();
        when(repository.findAll()).thenReturn(articles);

        // when
        Optional<List<ArticleResponse>> result = service.getAllArticles();

        // then
        assertTrue(result.isPresent());
        assertEquals(10, result.get().size());
    }

    @Test
    void shouldReturnArticleById() {
        // given
        List<Article> articles = generateArticles();
        when(repository.findById(1L)).thenReturn(Optional.of(articles.getFirst()));

        // when
        Optional<ArticleResponse> result = service.getArticleById(1L);

        // then
        assertTrue(result.isPresent());
        assertEquals("Article 0", result.get().getTitle());
        verify(producer, times(1)).sendViews(any(ArticleViewsEvent.class));
    }

    @Test
    void shouldDeleteArticle() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(new Article()));

        // when
        String result = service.deleteArticle(1L);

        // then
        assertEquals("Article successfully deleted", result);
    }

    @Test
    void shouldGetArticlesByAuthor() {
        // given
        List<Article> articles = generateArticles();
        when(repository.findByAuthorName("michaldoroz")).thenReturn(articles);

        // when
        List<ArticleResponse> result = service.getArticlesByAuthor("michaldoroz");

        // then
        assertEquals(10, result.size());
    }

    private List<Article> generateArticles() {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setId((long) i);
            article.setTitle("Article " + i);
            article.setCreatedAt(Instant.now());
            article.setViews(0L);
            article.setCommentIds(new ArrayList<>());
            article.setAuthorName("michaldoroz");
            articles.add(article);
        }
        return articles;
    }
}
