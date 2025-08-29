package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.common.enums.CategorySortBy;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryRequest;
import com.practice.spring_mini_project_01_group01.dto.category.CategoryResponse;
import com.practice.spring_mini_project_01_group01.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping()
  private APIResponse<CategoryResponse> createCategory(
      @RequestBody CategoryRequest categoryRequest) {
    return categoryService.createCategory(categoryRequest);
  }

  @GetMapping
  public APIResponse<Page<CategoryResponse>> getAllCategory(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "ID") CategorySortBy sortBy,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
    Page<CategoryResponse> categories =
        categoryService.getAllCategories(page, size, sortBy, direction);

    return new APIResponse<>(
        "Fetched categories successfully", categories, HttpStatus.OK, LocalDateTime.now());
  }

  @GetMapping("/{id}")
  public APIResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
    CategoryResponse response = categoryService.getCategoryById(id);

    return new APIResponse<>(
        "Category fetched successfully", response, HttpStatus.OK, LocalDateTime.now());
  }

  @PutMapping("/{id}")
  public APIResponse<CategoryResponse> updateCategory(
      @PathVariable Long id, @RequestBody CategoryRequest request) {
    CategoryResponse updated = categoryService.updateCategory(id, request);

    return new APIResponse<>(
        "Category updated successfully", updated, HttpStatus.OK, LocalDateTime.now());
  }

  @DeleteMapping("/{id}")
  public APIResponse<String> deleteCategory(@PathVariable Long id) {
    String deleteCategory = categoryService.deleteCategory(id);

    return new APIResponse<>(
        "Category deleted successfully", deleteCategory, HttpStatus.OK, LocalDateTime.now());
  }
}
