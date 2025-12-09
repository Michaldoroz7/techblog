package com.doroz.article_service.repository;


import com.doroz.article_service.model.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthorName(String authorName);

    @EntityGraph(attributePaths = "commentIds")
    Optional<Article> findById(Long id);

    @Modifying
    @Query("update Article a set a.views = a.views + 1 where a.id = :id")
    void incrementViewsById(Long id);

}