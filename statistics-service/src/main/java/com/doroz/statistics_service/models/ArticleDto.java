package com.doroz.statistics_service.models;

import java.time.LocalDateTime;


public record ArticleDto(Long id, String title, LocalDateTime createdAt) {

}
