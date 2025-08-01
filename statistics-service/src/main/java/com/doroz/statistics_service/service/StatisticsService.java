package com.doroz.statistics_service.service;

import com.doroz.statistics_service.client.ArticleClient;
import com.doroz.statistics_service.client.CommentClient;
import com.doroz.statistics_service.client.UserClient;
import com.doroz.statistics_service.models.ArticleDto;
import com.doroz.statistics_service.models.CommentDto;
import com.doroz.statistics_service.models.StatisticsResponse;
import com.doroz.statistics_service.models.UserDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class StatisticsService {

    private final ArticleClient articleClient;
    private final CommentClient commentClient;
    private final UserClient userClient;

    public StatisticsService(ArticleClient articleClient, CommentClient commentClient, UserClient userClient) {
        this.articleClient = articleClient;
        this.commentClient = commentClient;
        this.userClient = userClient;
    }

    public StatisticsResponse getStatistics() {
        List<ArticleDto> articles = articleClient.findAll();
        List<CommentDto> comments = commentClient.findAll();
        List<UserDto> users = userClient.findAll();

        return new StatisticsResponse(
                articles.size(),
                comments.size(),
                users.size(),
                articles.stream().max(Comparator.comparing(ArticleDto::getCreatedAt)).orElse(null),
                comments.stream().max(Comparator.comparing(CommentDto::getCreatedAt)).orElse(null),
                users.stream().max(Comparator.comparing(UserDto::getCreatedAt)).orElse(null)
        );
    }
}

