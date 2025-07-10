package com.doroz.comment_service.event;

import com.doroz.events.CommentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventProducer {

    private final KafkaTemplate<String, CommentCreatedEvent> kafkaTemplate;

    public void send(CommentCreatedEvent event) {
        kafkaTemplate.send("comment.created", event);
        System.out.println("[KafkaProduced] Sent: " + event);
    }
}
