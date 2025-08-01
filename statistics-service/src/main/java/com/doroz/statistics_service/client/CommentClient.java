package com.doroz.statistics_service.client;

import com.doroz.statistics_service.models.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "comment-service")
public interface CommentClient {
    @GetMapping("/api/comments")
    List<CommentDto> findAll();
}

