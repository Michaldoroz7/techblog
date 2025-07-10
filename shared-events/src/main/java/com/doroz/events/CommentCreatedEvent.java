package com.doroz.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreatedEvent {
    private Long articleId;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
}
