package com.practice.spring_mini_project_01_group01.dto.category;

import com.practice.spring_mini_project_01_group01.model.Category;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

  private String categoryName;

  public Category toEntity() {
    Category category = new Category();
    category.setCategoryName(this.categoryName);
    category.setAmountOfArticle(0); // default
    category.setCreatedAt(LocalDateTime.now());
    category.setUpdatedAt(LocalDateTime.now());
    return category;
  }
}
