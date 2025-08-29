package com.practice.spring_mini_project_01_group01.dto.commet;

import com.practice.spring_mini_project_01_group01.model.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
  private Long id;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long userId;

  public Comment toComment() {
    return new Comment(this.id, this.content, null, null, null, null);
  }
}
