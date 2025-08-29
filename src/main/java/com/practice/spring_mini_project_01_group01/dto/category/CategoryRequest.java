package com.practice.spring_mini_project_01_group01.dto.category;

import com.practice.spring_mini_project_01_group01.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

  @NotBlank(message = "Category name cannot be empty")
  @Pattern(
      regexp = "^[a-zA-Z0-9_-]+$",
      message =
          "Category name can only contain letters, numbers, underscores, or hyphens (no spaces or emojis)")
  @Schema(defaultValue = "Meme")
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
