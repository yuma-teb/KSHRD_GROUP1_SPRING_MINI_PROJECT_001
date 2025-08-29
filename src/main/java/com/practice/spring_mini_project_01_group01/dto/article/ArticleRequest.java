package com.practice.spring_mini_project_01_group01.dto.article;

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
  private String title;
  private String description;
  private List<Long> categoryIds;
}
