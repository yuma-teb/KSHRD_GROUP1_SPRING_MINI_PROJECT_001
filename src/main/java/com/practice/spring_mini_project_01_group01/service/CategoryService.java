package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.common.enums.CategorySortBy;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryRequest;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CategoryService {
  APIResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest);

  Page<CategoryResponse> getAllCategories(
      int page, int size, CategorySortBy sortBy, Sort.Direction direction);

  CategoryResponse getCategoryById(Long id);

  CategoryResponse updateCategory(Long id, CategoryRequest request);

  String deleteCategory(Long id);
}
