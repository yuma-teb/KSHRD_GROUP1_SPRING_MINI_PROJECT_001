package com.practice.spring_mini_project_01_group01.dto.category;

import com.practice.spring_mini_project_01_group01.model.Category;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
  private Long categoryId;
  private String categoryName;
  private int amountOfArticles;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static CategoryResponse fromEntity(Category category) {
    CategoryResponse response = new CategoryResponse();
    response.setCategoryId(category.getId());
    response.setCategoryName(category.getCategoryName());
    response.setAmountOfArticles(category.getAmountOfArticle());
    response.setCreatedAt(LocalDateTime.now());
    response.setUpdatedAt(LocalDateTime.now());
    return response;
  }
}
