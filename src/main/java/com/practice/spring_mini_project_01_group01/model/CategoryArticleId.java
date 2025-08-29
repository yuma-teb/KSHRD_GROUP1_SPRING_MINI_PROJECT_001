package com.practice.spring_mini_project_01_group01.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.*;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryArticleId implements Serializable {

  private Long articleId;
  private Long categoryId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CategoryArticleId that = (CategoryArticleId) o;
    return Objects.equals(articleId, that.articleId) && Objects.equals(categoryId, that.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleId, categoryId);
  }
}
