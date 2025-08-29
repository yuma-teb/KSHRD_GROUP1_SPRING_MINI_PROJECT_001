package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentRequest;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentResponse;
import com.practice.spring_mini_project_01_group01.model.Article;

public interface CommentService {
  APIResponse<CommentResponse> getCommentById(Long commentId);

  APIResponse<CommentResponse> updateComment(Long commentId, CommentRequest commentRequest);

  void createComment(Article article, CommentRequest content);

  APIResponse<CommentResponse> deleteComment(Long commentId);
}
