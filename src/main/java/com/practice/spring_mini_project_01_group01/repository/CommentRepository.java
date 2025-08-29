package com.practice.spring_mini_project_01_group01.repository;

import com.practice.spring_mini_project_01_group01.model.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Optional<Comment> findByIdAndUserId(Long commentId, Long userId);

  @Query(value = "SELECT * FROM comments WHERE article_id = :articleArticleId", nativeQuery = true)
  List<Comment> findCommentsByArticle_ArticleId(Long articleArticleId);

  @Query(
      value = "SELECT * FROM comments WHERE user_id = :userId AND article_id = :articleId",
      nativeQuery = true)
  List<Comment> findByUserIdAndArticleId(Long userId, Long articleId);
}
