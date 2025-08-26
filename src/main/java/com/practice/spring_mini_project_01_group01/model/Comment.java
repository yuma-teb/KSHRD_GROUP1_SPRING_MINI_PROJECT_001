package com.practice.spring_mini_project_01_group01.model;

import com.practice.spring_mini_project_01_group01.model.dto.response.CommentResponse;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  private Long articleId;

  private Long userId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public CommentResponse toResponse() {
    return new CommentResponse(null, this.content, this.createdAt, this.updatedAt, this.userId);
  }
}
