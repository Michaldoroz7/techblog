package com.doroz.comment_service.service;

import com.doroz.comment_service.model.Comment;
import com.doroz.comment_service.model.CommentRequest;
import com.doroz.comment_service.model.CommentResponse;
import com.doroz.comment_service.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public CommentResponse create(CommentRequest request, String username) {
        Comment comment = Comment.builder()
                .articleId(request.articleId())
                .content(request.content())
                .authorUsername(username)
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = repository.save(comment);
        return toDto(saved);
    }

    public List<CommentResponse> getByArticle(Long articleId) {
        return repository.findByArticleId(articleId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private CommentResponse toDto(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getArticleId(),
                comment.getContent(),
                comment.getAuthorUsername(),
                comment.getCreatedAt()
        );
    }

}
