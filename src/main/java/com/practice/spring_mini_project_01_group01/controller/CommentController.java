package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.model.dto.commet.CommentRequest;
import com.practice.spring_mini_project_01_group01.model.dto.commet.CommentResponse;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;

  @GetMapping("/{commentId}")
  private CommentResponse getCommentById(@PathVariable("commentId") Long commentId) {
    return commentService.getCommentById(commentId);
  }

  @PutMapping("/{commentId}")
  private CommentResponse updateComment(
      @PathVariable("commentId") Long commentId, @RequestBody CommentRequest commentRequest) {
    return commentService.updateComment(commentId, commentRequest);
  }

  @DeleteMapping("/{commentId}")
  private void deleteComment(@PathVariable("commentId") Long commentId) {
    commentService.deleteComment(commentId);
  }
}
