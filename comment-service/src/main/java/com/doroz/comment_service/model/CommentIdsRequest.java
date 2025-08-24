package com.doroz.comment_service.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CommentIdsRequest(
        @NotNull
        List<Long> ids) {

}

