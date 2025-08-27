package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryRequest;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryResponse;

public interface CategoryService {
  APIResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest);
}
