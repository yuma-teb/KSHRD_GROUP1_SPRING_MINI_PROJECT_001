package com.practice.spring_mini_project_01_group01.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practice.spring_mini_project_01_group01.dto.commet.CommentResponse;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference
  private User user;

  @Builder.Default private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime updatedAt;

  public CommentResponse toResponse() {
    return new CommentResponse(
        this.id, this.content, this.createdAt, this.updatedAt, this.user.getId());
  }
}
