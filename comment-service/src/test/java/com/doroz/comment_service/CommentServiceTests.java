package com.doroz.comment_service;


import com.doroz.comment_service.event.CommentEventProducer;
import com.doroz.comment_service.model.Comment;
import com.doroz.comment_service.model.CommentIdsRequest;
import com.doroz.comment_service.model.CommentRequest;
import com.doroz.comment_service.model.CommentResponse;
import com.doroz.comment_service.repository.CommentRepository;
import com.doroz.comment_service.service.CommentService;
import com.doroz.events.CommentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentEventProducer producer;

    @InjectMocks
    private CommentService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateComment() {
        // given
        CommentRequest request = new CommentRequest(1L, "Test content");
        String username = "michaldoroz";

        Comment savedComment = Comment.builder()
                .id(1L)
                .articleId(1L)
                .content("Test content")
                .authorUsername("michaldoroz")
                .createdAt(java.time.LocalDateTime.now())
                .build();
                
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // when
        Optional<CommentResponse> result = service.create(request, username);

        // then
        assertTrue(result.isPresent());
        assertEquals("Test content", result.get().content());
        verify(producer, times(1)).sendCreate(any(CommentEvent.class));
    }

    @Test
    void shouldGetCommentsByArticleId() {
        // given
        List<Comment> comments = generateComments();
        when(commentRepository.findByArticleId(1L)).thenReturn(comments);

        // when
        Optional<List<CommentResponse>> result = service.getByArticle(1L);

        // then
        assertTrue(result.isPresent());
        assertEquals(10, result.get().size());
    }

    @Test
    void shouldGetAllComments() {
        // given
        List<Comment> comments = generateComments();
        when(commentRepository.findAll()).thenReturn(comments);

        // when
        Optional<List<CommentResponse>> result = service.getAllComments();

        // then
        assertTrue(result.isPresent());
        assertEquals(10, result.get().size());
    }

    @Test
    void shouldDeleteComment() {
        // given
        when(commentRepository.findById(1L)).thenReturn(Optional.of(new Comment()));

        // when
        String result = service.deleteComment(1L, "michaldoroz");

        // then
        assertEquals("Comment successfuly deleted", result);
    }

    @Test
    void shouldGetCommentById() {
        // given
        List<Comment> comments = generateComments();
        when(commentRepository.findAllById(any())).thenReturn(comments.subList(0, 2));

        // when
        CommentIdsRequest request = new CommentIdsRequest();
        request.setIds(List.of(1L, 2L));
        List<CommentResponse> result = service.getByIds(request).get();

        // then
        assertEquals(2, result.size());
        assertEquals("Comment 0", result.get(0).content());
    }


    private List<Comment> generateComments() {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment();
            comment.setId((long) i);
            comment.setArticleId(1L);
            comment.setContent("Comment " + i);
            comment.setAuthorUsername("michaldoroz");
            comment.setCreatedAt(java.time.LocalDateTime.now());
            comments.add(comment);
        }
        return comments;
    }

}
