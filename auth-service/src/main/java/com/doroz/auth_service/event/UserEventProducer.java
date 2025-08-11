package com.doroz.auth_service.event;


import com.doroz.events.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> userEventKafkaTemplate;

    public void sendActivity(UserEvent event) {
        userEventKafkaTemplate.send("activity.event", event);
        System.out.println("[KafkaProducer] Sent: " + event);
    }
}
