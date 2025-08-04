package com.doroz.statistics_service.event;

import com.doroz.statistics_service.models.Activity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ActivityStorage {
    private final List<Activity> activityEvents = new CopyOnWriteArrayList<>();

    public void save(Activity event) {
        activityEvents.add(event);
    }

    public List<Activity> getLatest(int limit) {
        return activityEvents.stream()
                .sorted(Comparator.comparing(Activity::getTimestamp).reversed())
                .limit(limit)
                .toList();
    }

    public List<Activity> getAll() {
        return List.copyOf(activityEvents);
    }

    public void clear() {
        activityEvents.clear();
    }
}

