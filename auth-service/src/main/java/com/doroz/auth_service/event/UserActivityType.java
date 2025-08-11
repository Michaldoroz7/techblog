package com.doroz.auth_service.event;

import lombok.Getter;

@Getter
public enum UserActivityType {
    CREATED("Created"),
    UPDATED("Updated"),
    DELETED("Deleted");

    private final String label;

    UserActivityType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}

