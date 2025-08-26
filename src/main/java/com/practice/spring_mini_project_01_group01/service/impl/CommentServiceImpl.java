package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.model.Comment;
import com.practice.spring_mini_project_01_group01.model.dto.response.CommentResponse;
import com.practice.spring_mini_project_01_group01.repository.CommentRepository;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;

  @Override
  public CommentResponse getCommentById(Long commentId) {
    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment Id not found"));
    return comment.toResponse();
  }
}
