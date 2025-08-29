package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.common.enums.CategorySortBy;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    if (categoryRepository.existsByCategoryNameIgnoreCase(categoryRequest.getCategoryName())) {
      throw new NotFoundException(
          "Category with name '" + categoryRequest.getCategoryName() + "' already exists");
    }

    Category category = categoryRequest.toEntity();
    category = categoryRepository.save(category);

    CategoryResponse response = CategoryResponse.fromEntity(category);

    return new APIResponse<>(
        "Fetch Custom Successfully", response, HttpStatus.OK, LocalDateTime.now());
  }

  @Override
  public Page<CategoryResponse> getAllCategories(
      int page, int size, CategorySortBy sortBy, Sort.Direction direction) {

    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    Sort sort =
        (direction == Sort.Direction.DESC)
            ? Sort.by(sortBy.getField()).descending()
            : Sort.by(sortBy.getField()).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return categoryRepository.findAll(pageable).map(CategoryResponse::fromEntity);
  }

  @Override
  public CategoryResponse getCategoryById(Long id) {

    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Category not found with id " + id));

    return CategoryResponse.fromEntity(category);
  }

  @Override
  public CategoryResponse updateCategory(Long id, CategoryRequest request) {
    Category category =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Category not found with id " + id));

    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    if (request.getCategoryName() != null
        && !request.getCategoryName().equalsIgnoreCase(category.getCategoryName())
        && categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
      throw new NotFoundException(
          "Category with name '" + request.getCategoryName() + "' already exists");
    }

    if (request.getCategoryName() != null) {
      category.setCategoryName(request.getCategoryName());
    }

    category.setUpdatedAt(LocalDateTime.now());

    categoryRepository.save(category);

    return CategoryResponse.fromEntity(category);
  }

  @Override
  public String deleteCategory(Long id) {

    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }
    if (!categoryRepository.existsById(id)) {
      throw new RuntimeException("Category not found with id " + id);
    }

    categoryRepository.deleteById(id);

    return "Deleted Category!";
  }
}
