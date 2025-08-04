package com.doroz.comment_service.event;

import com.doroz.events.ActivityEvent;
import com.doroz.events.CommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventProducer {

    private final KafkaTemplate<String, CommentEvent> commentEventKafkaTemplate;
    private final KafkaTemplate<String, ActivityEvent> activityEventKafkaTemplate;

    public void sendCreate(CommentEvent event) {
        commentEventKafkaTemplate.send("comment.created", event);
        System.out.println("[KafkaProducer] Sent: " + event);
    }

    public void sendDelete(CommentEvent event) {
        commentEventKafkaTemplate.send("comment.deleted", event);
        System.out.println("[KafkaProducer] Sent:" + event);
    }

    public void sendActivity(ActivityEvent event) {
        activityEventKafkaTemplate.send("activity.event", event);
        System.out.println("[KafkaProducer] Sent: " + event);
    }
}
