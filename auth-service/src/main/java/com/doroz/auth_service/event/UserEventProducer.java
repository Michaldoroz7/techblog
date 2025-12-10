package com.doroz.auth_service.event;


import com.doroz.events.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> userEventKafkaTemplate;

    public void sendActivity(UserEvent event) {
        sendAsync(userEventKafkaTemplate, event);
    }

    private <T> void sendAsync(KafkaTemplate<String, T> template, T event) {
        template.send("user.event", event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("[KafkaProducer] Sent event to " + "user.event" + " offset=" + result.getRecordMetadata().offset());
                    } else {
                        log.error("[KafkaProducer] Failed to send event to " + "user.event" + ": " + ex.getMessage());
                    }
                });
    }
}
