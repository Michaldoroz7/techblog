package com.doroz.comment_service.controller;

import com.doroz.comment_service.model.CommentIdsRequest;
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

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        return ResponseEntity.of(service.getAllComments());
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentRequest request, @RequestHeader("X-Username") String username) {
        return ResponseEntity.of(service.create(request, username));
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentResponse>> getByArticle(@PathVariable Long articleId) {
        return ResponseEntity.of(service.getByArticle(articleId));
    }

    @PostMapping("/article/by-ids")
    public ResponseEntity<List<CommentResponse>> getByIds(@RequestBody CommentIdsRequest commentIdsRequest) {
        return ResponseEntity.of(service.getByIds(commentIdsRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(service.deleteComment(id, username));
    }
}
