package com.doroz.article_service.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private final LocalDateTime timestamp;

    private final int status;

    private final String message;

    private final String path;

    private final Map<String, String> details;

}
