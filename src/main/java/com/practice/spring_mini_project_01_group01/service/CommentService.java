package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.model.dto.response.CommentResponse;

public interface CommentService {
  CommentResponse getCommentById(Long commentId);
}
