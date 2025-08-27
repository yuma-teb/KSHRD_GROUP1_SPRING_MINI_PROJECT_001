package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.common.utils.AuthUtil;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryRequest;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryResponse;
import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.Category;
import com.practice.spring_mini_project_01_group01.repository.CategoryRepository;
import com.practice.spring_mini_project_01_group01.service.CategoryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final AuthUtil authUtil;

  @Override
  public APIResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest) {

    System.out.println("user is " + authUtil.getCurrentUserRole());

    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    Category category = categoryRequest.toEntity();
    category = categoryRepository.save(category);

    CategoryResponse response = CategoryResponse.fromEntity(category);

    return new APIResponse<>(
        "Fetch Custom Successfully", response, HttpStatus.OK, LocalDateTime.now());
  }
}
