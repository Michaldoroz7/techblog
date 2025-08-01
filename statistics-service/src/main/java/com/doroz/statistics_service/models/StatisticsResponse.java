package com.doroz.statistics_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponse {

    private int totalArticles;
    private int totalComments;
    private int totalUsers;

    private ArticleDto lastArticle;
    private CommentDto lastComment;
    private UserDto lastUser;
}
