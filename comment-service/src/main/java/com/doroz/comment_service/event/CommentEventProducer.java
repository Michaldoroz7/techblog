package com.doroz.comment_service.event;

import com.doroz.events.CommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventProducer {

    private final KafkaTemplate<String, CommentEvent> kafkaTemplate;

    public void sendCreate(CommentEvent event) {
        kafkaTemplate.send("comment.created", event);
        System.out.println("[KafkaProduced] Sent: " + event);
    }

    public void sendDelete(CommentEvent event) {
        kafkaTemplate.send("comment.deleted", event);
        System.out.println("[KafkaProduced] Sent:" + event);
    }
}
