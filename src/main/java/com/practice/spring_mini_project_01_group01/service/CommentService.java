package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.model.dto.commet.CommentRequest;
import com.practice.spring_mini_project_01_group01.model.dto.commet.CommentResponse;

public interface CommentService {
  CommentResponse getCommentById(Long commentId);

  CommentResponse updateComment(Long commentId, CommentRequest commentRequest);

  void deleteComment(Long commentId);
}
