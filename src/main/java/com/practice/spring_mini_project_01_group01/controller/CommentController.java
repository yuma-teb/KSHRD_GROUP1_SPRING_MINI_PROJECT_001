package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.model.dto.response.CommentResponse;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;

  @GetMapping("/{commentId}")
  private CommentResponse getCommentById(@PathVariable("commentId") Long commentId) {
    return commentService.getCommentById(commentId);
  }
}
