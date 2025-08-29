package com.practice.spring_mini_project_01_group01.dto.article;

import com.practice.spring_mini_project_01_group01.model.Article;
import com.practice.spring_mini_project_01_group01.model.Category;
import com.practice.spring_mini_project_01_group01.model.CategoryArticle;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
  private List<String> categories;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ArticleResponse fromArticle(Article article) {
    Long userId = (article.getUser() != null) ? article.getUser().getId() : null;

    List<String> categoryNames =
        article.getArticleCategories().stream()
            .map(CategoryArticle::getCategory)
            .map(Category::getCategoryName)
            .collect(Collectors.toList());

    return ArticleResponse.builder()
        .articleId(article.getArticleId())
        .title(article.getTitle())
        .description(article.getDescription())
        .userId(userId)
        .categories(categoryNames)
        .createdAt(article.getCreatedAt())
        .updatedAt(article.getUpdatedAt() != null ? article.getUpdatedAt() : null)
        .build();
  }
}
