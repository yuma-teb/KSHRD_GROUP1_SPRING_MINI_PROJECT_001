package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.commet.CommentRequest;
import com.practice.spring_mini_project_01_group01.dto.commet.CommentResponse;
import com.practice.spring_mini_project_01_group01.model.Article;

public interface CommentService {
  APIResponse<CommentResponse> getCommentById(Long commentId);

  APIResponse<CommentResponse> updateComment(Long commentId, CommentRequest commentRequest);

  void createComment(String content, Article article);

  APIResponse<CommentResponse> deleteComment(Long commentId);
}
