package com.practice.spring_mini_project_01_group01.dto.article;

import com.practice.spring_mini_project_01_group01.model.Article;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {

  private Long articleId;
  private String title;
  private String description;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ArticleResponse fromArticle(Article article) {
    Long userId = (article.getUser() != null) ? article.getUser().getId() : null;
    return ArticleResponse.builder()
        .articleId(article.getArticleId())
        .title(article.getTitle())
        .description(article.getDescription())
        .userId(userId)
        .createdAt(article.getCreatedAt())
        .updatedAt(article.getUpdatedAt() != null ? article.getUpdatedAt() : null)
        .build();
  }
}
