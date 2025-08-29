package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.common.enums.ArticleProperty;
import com.practice.spring_mini_project_01_group01.common.utils.AuthUtil;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentRequest;
import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.*;
import com.practice.spring_mini_project_01_group01.repository.ArticleRepository;
import com.practice.spring_mini_project_01_group01.repository.CategoryRepository;
import com.practice.spring_mini_project_01_group01.service.ArticleService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;
  private final AuthUtil authUtil;

  @Override
  public ArticleResponse save(ArticleRequest articleRequest) {

    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Article article =
        Article.builder()
            .title(articleRequest.getTitle())
            .description(articleRequest.getDescription())
            .user(currentUser)
            .articleCategories(new ArrayList<>())
            .build();

    Article savedArticle = articleRepository.save(article);

    if (articleRequest.getCategoryIds() != null && !articleRequest.getCategoryIds().isEmpty()) {
      List<Category> categories = categoryRepository.findAllById(articleRequest.getCategoryIds());
      for (Category category : categories) {
        CategoryArticleId id = new CategoryArticleId(savedArticle.getArticleId(), category.getId());
        CategoryArticle categoryArticle =
            CategoryArticle.builder().id(id).article(savedArticle).category(category).build();
        savedArticle.getArticleCategories().add(categoryArticle);

        category.setAmountOfArticle(category.getAmountOfArticle() + 1);
        categoryRepository.save(category);
      }

      savedArticle = articleRepository.saveAndFlush(savedArticle);
    }

    return ArticleResponse.fromArticle(savedArticle);
  }

  @Override
  public APIResponse<List<ArticleResponse>> findAll(
      int page, int size, ArticleProperty articleProperty, Sort.Direction direction) {

    if (page < 1) page = 1;
    if (size < 1) size = 10;

    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, articleProperty.name()));

    Page<Article> articlesPage = articleRepository.findAll(pageable);

    List<ArticleResponse> articleResponses =
        articlesPage.getContent().stream()
            .map(ArticleResponse::fromArticle)
            .collect(Collectors.toList());

    return new APIResponse<>(
        "Get all articles successfully.", articleResponses, HttpStatus.OK, LocalDateTime.now());
  }

  @Override
  public ArticleResponse addComment(Long articleId, CommentRequest commentRequest) {
    return null;
  }

  @Override
  public APIResponse<Void> deteleArticle(Long articleId) {

    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));

    for (CategoryArticle ca : article.getArticleCategories()) {
      Category category = ca.getCategory();
      category.setAmountOfArticle(Math.max(0, category.getAmountOfArticle() - 1));
      categoryRepository.save(category);
    }
    articleRepository.delete(article);

    // Return a success response
    return new APIResponse<>(
        "Article deleted successfully", null, HttpStatus.OK, LocalDateTime.now());
  }

  @Override
  public APIResponse<ArticleResponse> getArticleById(Long articleId) {
    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new NotFoundException("Article not found with ID: " + articleId));

    ArticleResponse articleResponse = ArticleResponse.fromArticle(article);

    // Return as APIResponse
    return new APIResponse<>(
        "Get article successfully", articleResponse, HttpStatus.OK, LocalDateTime.now());
  }

  @Override
  @Transactional
  public ArticleResponse updateArticle(Long articleId, ArticleRequest articleRequest) {
    // Role check
    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    // Load existing article
    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new NotFoundException("Article not found with ID: " + articleId));

    // Update basic fields
    article.setTitle(articleRequest.getTitle());
    article.setDescription(articleRequest.getDescription());

    // Compute existing and new category IDs
    List<Long> existingCategoryIds =
        article.getArticleCategories().stream()
            .map(ca -> ca.getCategory().getId())
            .toList(); // existing id from db

    List<Long> newCategoryIds =
        articleRequest.getCategoryIds() != null
            ? articleRequest.getCategoryIds()
            : List.of(); // categoryId from request

    //  Remove associations that are no longer needed like links from each category to article
    article
        .getArticleCategories()
        .removeIf(
            ca -> {
              Long categoryId = ca.getCategory().getId();
              if (!newCategoryIds.contains(categoryId)) {
                // Decrease amountOfArticles safely
                Category category = ca.getCategory();
                category.setAmountOfArticle(Math.max(0, category.getAmountOfArticle() - 1));

                categoryRepository.save(category);

                // Detach
                ca.setArticle(null);
                ca.setCategory(null);
                return true;
              }
              return false;
            });

    // 6 Add only new associations
    List<Long> toAdd =
        newCategoryIds.stream().filter(id -> !existingCategoryIds.contains(id)).toList();

    if (!toAdd.isEmpty()) {
      List<Category> categoriesToAdd = categoryRepository.findAllById(toAdd);
      for (Category category : categoriesToAdd) {
        CategoryArticleId id = new CategoryArticleId(article.getArticleId(), category.getId());
        CategoryArticle newCA =
            CategoryArticle.builder().id(id).article(article).category(category).build();
        article.getArticleCategories().add(newCA);

        // Increase amountOfArticles
        category.setAmountOfArticle(category.getAmountOfArticle() + 1);

        categoryRepository.save(category);
      }
    }

    // Save and return
    Article updatedArticle = articleRepository.saveAndFlush(article);

    return ArticleResponse.fromArticle(updatedArticle);
  }
}
