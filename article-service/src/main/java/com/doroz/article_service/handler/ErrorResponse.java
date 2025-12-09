package com.doroz.article_service.handler;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(LocalDateTime timestamp, int status, String message, String path,
                            Map<String, String> details) {

}
