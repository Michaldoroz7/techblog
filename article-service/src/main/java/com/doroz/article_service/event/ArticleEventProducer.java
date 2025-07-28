package com.doroz.article_service.event;


import com.doroz.events.ArticleViewsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleEventProducer {

    private final KafkaTemplate<String, ArticleViewsEvent> kafkaTemplate;

    public void sendViews(ArticleViewsEvent event) {
        kafkaTemplate.send("article.views", event);
        System.out.println("[KafkaProducer] Sent: " + event);
    }
}
