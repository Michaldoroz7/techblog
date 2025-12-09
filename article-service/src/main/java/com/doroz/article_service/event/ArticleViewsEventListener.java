package com.doroz.article_service.event;

import com.doroz.article_service.repository.ArticleRepository;
import com.doroz.events.ArticleViewsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleViewsEventListener {

    private final ArticleRepository articleRepository;

    @KafkaListener(topics = "article.views", groupId = "article-service-group")
    public void handleArticleView(ArticleViewsEvent event) {
        articleRepository.incrementViewsById(event.getArticleId());
    }
}
