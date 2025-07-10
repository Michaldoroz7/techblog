package com.doroz.article_service.event;

import com.doroz.article_service.model.CommentResponse;
import com.doroz.events.CommentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CommentEventListener {

    @KafkaListener(topics = "comment.created", groupId = "article-service-group")
    public void handle(CommentCreatedEvent event) {

        CommentResponse comment = CommentResponse.builder()
                .articleId(event.getArticleId())
                .content(event.getContent())
                .authorUsername(event.getAuthorUsername())
                .createdAt(event.getCreatedAt())
                .build();
        System.out.println("[KafkaListener] New comment: " + event);
    }
}
