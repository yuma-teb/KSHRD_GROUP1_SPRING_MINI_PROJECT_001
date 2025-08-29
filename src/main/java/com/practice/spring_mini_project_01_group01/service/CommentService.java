package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.commet.CommentRequest;
import com.practice.spring_mini_project_01_group01.dto.commet.CommentResponse;
import com.practice.spring_mini_project_01_group01.model.Article;

public interface CommentService {
  CommentResponse getCommentById(Long commentId);

  CommentResponse updateComment(Long commentId, CommentRequest commentRequest);

  void createComment(String content, Article article);

  void deleteComment(Long commentId);
}
