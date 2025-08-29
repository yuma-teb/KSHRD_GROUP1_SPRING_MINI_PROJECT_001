package com.practice.spring_mini_project_01_group01.repository;

import com.practice.spring_mini_project_01_group01.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByArticle_ArticleIdAndUser_Id(Long articleId, Long userId);

    Page<Bookmark> findByUserId(Long id, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    long deleteByArticle_ArticleIdAndUser_Id(Long articleId, Long userId);
}
