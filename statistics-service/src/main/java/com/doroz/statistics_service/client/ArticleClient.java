package com.doroz.statistics_service.client;

import com.doroz.statistics_service.models.ArticleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "article-service")
public interface ArticleClient {
    @GetMapping("/api/articles")
    List<ArticleDto> findAll();
}

