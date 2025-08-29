package com.practice.spring_mini_project_01_group01.common.enums;

import lombok.Getter;

@Getter
public enum CategorySortBy {
  ID("id"),
  CATEGORY_NAME("categoryName"),
  CREATED_AT("createdAt");

  private final String field;

  CategorySortBy(String field) {
    this.field = field;
  }
}
