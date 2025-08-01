package com.doroz.comment_service.service;

import com.doroz.comment_service.event.CommentEventProducer;
import com.doroz.comment_service.model.Comment;
import com.doroz.comment_service.model.CommentIdsRequest;
import com.doroz.comment_service.model.CommentRequest;
import com.doroz.comment_service.model.CommentResponse;
import com.doroz.comment_service.repository.CommentRepository;
import com.doroz.events.CommentEvent;
import org.springframework.stereotype.Service;

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

    public CommentResponse create(CommentRequest request, String username) {
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

        producer.sendCreate(event);
        return toDto(saved);
    }

    public List<CommentResponse> getAllComments() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
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

        producer.sendDelete(event);

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
