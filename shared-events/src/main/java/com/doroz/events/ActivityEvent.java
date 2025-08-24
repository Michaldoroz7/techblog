package com.doroz.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityEvent {
    private String username;
    private String action;
    private String entity;
    private Long entityId;
    private String description;
    private Instant timestamp;
}
