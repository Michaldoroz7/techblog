package com.doroz.comment_service.service;

import com.doroz.comment_service.event.CommentEventProducer;
import com.doroz.comment_service.model.Comment;
import com.doroz.comment_service.model.CommentIdsRequest;
import com.doroz.comment_service.model.CommentRequest;
import com.doroz.comment_service.model.CommentResponse;
import com.doroz.comment_service.repository.CommentRepository;
import com.doroz.events.CommentCreatedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final CommentEventProducer producer;

    public CommentService(CommentRepository repository, CommentEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public CommentResponse create(CommentRequest request, String username) {
        Comment comment = Comment.builder()
                .articleId(request.articleId())
                .content(request.content())
                .authorUsername(username)
                .createdAt(LocalDateTime.now())
                .build();

        CommentCreatedEvent event = CommentCreatedEvent.builder()
                .articleId(comment.getArticleId())
                .commentId(comment.getId())
                .build();

        producer.send(event);

        Comment saved = repository.save(comment);
        return toDto(saved);
    }

    public List<CommentResponse> getByArticle(Long articleId) {
        return repository.findByArticleId(articleId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<CommentResponse> getByIds(CommentIdsRequest commentIdsRequest) {
        return repository.findAllById(commentIdsRequest.getIds()).stream()
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
