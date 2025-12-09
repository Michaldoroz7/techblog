package com.doroz.article_service.event;


import com.doroz.events.ActivityEvent;
import com.doroz.events.ArticleViewsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArticleEventProducer {

    private final KafkaTemplate<String, ArticleViewsEvent> articleViewsEventKafkaTemplate;

    private final KafkaTemplate<String, ActivityEvent> activityEventKafkaTemplate;


    public void sendViews(ArticleViewsEvent event) {
        sendAsync(articleViewsEventKafkaTemplate, "article.views", event);
    }

    public void sendActivity(ActivityEvent event) {
        sendAsync(activityEventKafkaTemplate, "activity.event", event);
    }

    @Async("kafkaExecutor")
    private <T> void sendAsync(KafkaTemplate<String, T> template, String topic, T event) {
        template.send(topic, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("[KafkaProducer] Sent event to {} offset={}", topic, result.getRecordMetadata().offset());
                    } else {
                        log.error("[KafkaProducer] Failed to send event to {}: {}", topic, ex.getMessage());
                    }
                });
    }

}
