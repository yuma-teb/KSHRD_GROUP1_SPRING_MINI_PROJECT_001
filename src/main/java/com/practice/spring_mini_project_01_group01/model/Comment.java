package com.practice.spring_mini_project_01_group01.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practice.spring_mini_project_01_group01.model.dto.commet.CommentResponse;
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

  @ManyToOne
  @JoinColumn(name = "article_id")
  @JsonBackReference
  private Article article;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public CommentResponse toResponse() {
    return new CommentResponse(null, this.content, this.createdAt, this.updatedAt, null);
  }
}
