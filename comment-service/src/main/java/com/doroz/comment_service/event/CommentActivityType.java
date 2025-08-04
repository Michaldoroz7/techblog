package com.doroz.comment_service.event;

import lombok.Getter;

@Getter
public enum CommentActivityType {
    CREATED("Created"),
    UPDATED("Updated"),
    DELETED("Deleted");

    private final String label;

    CommentActivityType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
