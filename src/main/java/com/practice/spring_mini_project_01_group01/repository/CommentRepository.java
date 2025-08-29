package com.practice.spring_mini_project_01_group01.repository;

import com.practice.spring_mini_project_01_group01.model.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Optional<Comment> findByIdAndUserId(Long commentId, Long userId);

  @Query(value = "SELECT * FROM comments WHERE user_id = :userId", nativeQuery = true)
  List<Comment> findByUserId(@Param("userId") Long userId);
}
