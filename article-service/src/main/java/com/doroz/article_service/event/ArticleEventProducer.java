package com.doroz.article_service.event;


import com.doroz.events.ActivityEvent;
import com.doroz.events.ArticleViewsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleEventProducer {

    private final KafkaTemplate<String, ArticleViewsEvent> articleViewsEventKafkaTemplate;

    private final KafkaTemplate<String, ActivityEvent> activityEventKafkaTemplate;

    public void sendViews(ArticleViewsEvent event) {
        articleViewsEventKafkaTemplate.send("article.views", event);
        System.out.println("[KafkaProducer] Sent: " + event);
    }

    public void sendActivity(ActivityEvent event) {
        activityEventKafkaTemplate.send("activity.event", event);
        System.out.println("[KafkaProducer] Sent: " + event);
    }
}
