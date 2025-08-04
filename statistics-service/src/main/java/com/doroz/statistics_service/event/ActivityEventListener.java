package com.doroz.statistics_service.event;

import com.doroz.events.ActivityEvent;
import com.doroz.statistics_service.models.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityEventListener {

    private final ActivityStorage storage;

    @KafkaListener(topics = "activity.event", groupId = "activity-service-group")
    public void handle(ActivityEvent event) {
        Activity activity = new Activity(
                null,
                event.getTimestamp(),
                event.getUsername(),
                event.getAction(),
                event.getEntity(),
                event.getEntityId(),
                event.getDescription()
        );
        storage.save(activity);
    }
}
