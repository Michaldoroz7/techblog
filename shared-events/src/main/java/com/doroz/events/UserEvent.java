package com.doroz.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    private String username;
    private String action;
    private String entity;
    private Long entityId;
    private String description;
    private Instant timestamp;
}
