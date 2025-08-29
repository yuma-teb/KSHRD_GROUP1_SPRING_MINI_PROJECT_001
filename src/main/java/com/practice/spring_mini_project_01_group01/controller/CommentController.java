package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.dto.commet.CommentRequest;
import com.practice.spring_mini_project_01_group01.dto.commet.CommentResponse;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
  private final CommentService commentService;

  @Operation(summary = "Get comment by comment id, can view only your own comment")
  @GetMapping("/{commentId}")
  private CommentResponse getCommentById(@PathVariable("commentId") Long commentId) {
    return commentService.getCommentById(commentId);
  }

  @Operation(summary = "Update comment by comment id, can update only your own comment")
  @PutMapping("/{commentId}")
  private CommentResponse updateComment(
      @PathVariable("commentId") Long commentId, @RequestBody CommentRequest commentRequest) {
    return commentService.updateComment(commentId, commentRequest);
  }

  @Operation(summary = "Delete comment by comment id, can delete only your own comment")
  @DeleteMapping("/{commentId}")
  private void deleteComment(@PathVariable("commentId") Long commentId) {
    commentService.deleteComment(commentId);
  }
}
