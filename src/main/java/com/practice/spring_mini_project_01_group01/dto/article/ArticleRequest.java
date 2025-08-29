package com.practice.spring_mini_project_01_group01.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRequest {
  @NotBlank(message = "Title is required") // must not be null or blank
  @Size(max = 255, message = "Title cannot exceed 255 characters")
  private String title;

  @NotBlank(message = "Description is required") // must not be null or blank
  private String description;

  @NotEmpty(message = "At least one category must be selected") // list must not be empty
  private List<Long> categoryIds;
}
