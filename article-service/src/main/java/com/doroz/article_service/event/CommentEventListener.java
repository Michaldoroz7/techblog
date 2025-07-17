package com.doroz.article_service.event;

import com.doroz.article_service.model.Article;
import com.doroz.article_service.repository.ArticleRepository;
import com.doroz.events.CommentEvent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CommentEventListener {

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    @KafkaListener(topics = "comment.created", groupId = "article-service-group")
    public void handleCreate(CommentEvent event) {
        Optional<Article> optionalArticle = articleRepository.findById(event.getArticleId());
        if (!optionalArticle.isPresent()) {
            System.out.println("Article not found: " + event.getArticleId());
            return;
        }

        Article article = optionalArticle.get();
        if (article.getCommentIds() == null) {
            article.setCommentIds(new ArrayList<>());
        }

        if (!article.getCommentIds().contains(event.getCommentId())) {
            article.getCommentIds().add(event.getCommentId());
        }
        articleRepository.save(article);

        System.out.println("[KafkaListener] Updated article comments: " + article);
    }

    @Transactional
    @KafkaListener(topics = "comment.deleted", groupId = "article-service-group")
    public void handleDelete(CommentEvent event) {
        Optional<Article> optionalArticle = articleRepository.findById(event.getArticleId());
        if (!optionalArticle.isPresent()) {
            System.out.println("Article not found: " + event.getArticleId());
            return;
        }

        Article article = optionalArticle.get();
        if (article.getCommentIds().contains(event.getCommentId())) {
            article.getCommentIds().remove(event.getCommentId());
            articleRepository.save(article);
            System.out.println("Removed commentId " + event.getCommentId() + " from article " + event.getArticleId());
        }
    }
}

