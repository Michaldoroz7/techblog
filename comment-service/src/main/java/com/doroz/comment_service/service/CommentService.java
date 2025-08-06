package com.doroz.comment_service.service;

import com.doroz.comment_service.event.CommentActivityType;
import com.doroz.comment_service.event.CommentEventProducer;
import com.doroz.comment_service.model.Comment;
import com.doroz.comment_service.model.CommentIdsRequest;
import com.doroz.comment_service.model.CommentRequest;
import com.doroz.comment_service.model.CommentResponse;
import com.doroz.comment_service.repository.CommentRepository;
import com.doroz.events.ActivityEvent;
import com.doroz.events.CommentEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final CommentEventProducer producer;

    public CommentService(CommentRepository repository, CommentEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public Optional<CommentResponse> create(CommentRequest request, String username) {
        Comment comment = Comment.builder()
                .articleId(request.articleId())
                .content(request.content())
                .authorUsername(username)
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = repository.save(comment);

        CommentEvent event = CommentEvent.builder()
                .articleId(saved.getArticleId())
                .commentId(saved.getId())
                .build();

        ActivityEvent activityEvent = new ActivityEvent(
                username, "Comment", CommentActivityType.CREATED.getLabel(), saved.getId(), "Comment was created", Instant.now()
        );

        producer.sendCreate(event);
        producer.sendActivity(activityEvent);

        return Optional.of(toDto(saved));
    }

    public Optional<List<CommentResponse>> getAllComments() {
        return Optional.of(repository.findAll().stream()
                .map(this::toDto)
                .toList());
    }

    public Optional<List<CommentResponse>> getByArticle(Long articleId) {
        return Optional.of(repository.findByArticleId(articleId)
                .stream()
                .map(this::toDto)
                .toList());
    }

    public Optional<List<CommentResponse>> getByIds(CommentIdsRequest commentIdsRequest) {
        return Optional.of(repository.findAllById(commentIdsRequest.getIds()).stream()
                .map(this::toDto)
                .toList());
    }

    public String deleteComment(Long commentId, String username) {
        Optional<Comment> comment = repository.findById(commentId);

        if (comment.isEmpty()) {
            return "Comment doesn't exist";
        }

        if (!Objects.equals(username, comment.get().getAuthorUsername())) {
            return "Username is different than author";
        }

        CommentEvent event = CommentEvent.builder()
                .articleId(comment.get().getArticleId())
                .commentId(comment.get().getId())
                .build();

        ActivityEvent activityEvent = new ActivityEvent(
                username, "Comment", CommentActivityType.DELETED.getLabel(), commentId, "Comment was created", Instant.now()
        );

        producer.sendDelete(event);
        producer.sendActivity(activityEvent);

        repository.deleteById(commentId);
        return "Comment successfuly deleted";
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
