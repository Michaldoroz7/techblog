package com.doroz.article_service.event;

import lombok.Getter;

@Getter
public enum ArticleActivityType {
    CREATED("Created"),
    UPDATED("Updated"),
    DELETED("Deleted");

    private final String label;

    ArticleActivityType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
