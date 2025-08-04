package com.doroz.statistics_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class Activity {

    private Long id;
    private Instant timestamp;
    private String username;
    private String action;
    private String entity;
    private Long entityId;
    private String description;
}
