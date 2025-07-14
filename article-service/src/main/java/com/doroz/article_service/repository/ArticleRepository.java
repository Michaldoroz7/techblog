package com.doroz.article_service.repository;


import com.doroz.article_service.model.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthorName(String authorName);

    @EntityGraph(attributePaths = "commentIds")
    Optional<Article> findById(Long id);

}