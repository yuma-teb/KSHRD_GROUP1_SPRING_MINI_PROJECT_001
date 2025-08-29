package com.practice.spring_mini_project_01_group01.repository;

import com.practice.spring_mini_project_01_group01.model.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
}
