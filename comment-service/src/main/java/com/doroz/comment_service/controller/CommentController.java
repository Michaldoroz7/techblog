package com.doroz.comment_service.controller;

import com.doroz.comment_service.model.CommentRequest;
import com.doroz.comment_service.model.CommentResponse;
import com.doroz.comment_service.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @Valid @RequestBody CommentRequest request,
            @RequestHeader("X-Username") String username
    ) {
        return ResponseEntity.ok(service.create(request, username));
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentResponse>> getByArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(service.getByArticle(articleId));
    }
}
