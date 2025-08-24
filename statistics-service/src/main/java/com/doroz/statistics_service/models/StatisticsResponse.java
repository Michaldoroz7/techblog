package com.doroz.statistics_service.models;

public record StatisticsResponse(
        int totalArticles,
        int totalComments,
        int totalUsers,
        ArticleDto lastArticle,
        CommentDto lastComment,
        UserDto lastUser) {

}
