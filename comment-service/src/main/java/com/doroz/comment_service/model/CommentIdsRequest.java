package com.doroz.comment_service.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CommentIdsRequest {

    @NotNull
    private List<Long> ids;
}

