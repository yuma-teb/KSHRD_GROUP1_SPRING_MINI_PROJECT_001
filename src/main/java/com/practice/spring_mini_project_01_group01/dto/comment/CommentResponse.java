package com.practice.spring_mini_project_01_group01.dto.comment;

import com.practice.spring_mini_project_01_group01.model.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
  private Long commentId;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long userId;

  public static CommentResponse fromComment(Comment comment) {
    Long userId = (comment.getUser() != null) ? comment.getUser().getId() : null;

    return CommentResponse.builder()
        .commentId(comment.getId())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt() != null ? comment.getUpdatedAt() : null)
        .userId(userId)
        .build();
  }
}
