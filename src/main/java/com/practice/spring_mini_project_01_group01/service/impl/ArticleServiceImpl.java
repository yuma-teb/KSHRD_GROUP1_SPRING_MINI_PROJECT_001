package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.common.enums.ArticleProperty;
import com.practice.spring_mini_project_01_group01.common.utils.AuthUtil;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentRequest;
import com.practice.spring_mini_project_01_group01.exception.BadRequestException;
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

    if (articleRequest.getCategoryIds() != null) {

      if (articleRequest.getCategoryIds() == null || articleRequest.getCategoryIds().isEmpty()) {
        throw new BadRequestException("At least one category is required");
      }

      List<Long> requestedIds = articleRequest.getCategoryIds();
      List<Category> categories = categoryRepository.findAllById(requestedIds);

      List<Long> foundIds = categories.stream().map(Category::getId).toList();

      List<Long> missingIds = requestedIds.stream().filter(id -> !foundIds.contains(id)).toList();

      if (!missingIds.isEmpty()) {
        throw new NotFoundException("Categories not found: " + missingIds);
      }

      for (Category category : categories) {
        CategoryArticleId id = new CategoryArticleId(savedArticle.getArticleId(), category.getId());
        CategoryArticle categoryArticle =
            CategoryArticle.builder().id(id).article(savedArticle).category(category).build();
        savedArticle.getArticleCategories().add(categoryArticle);
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
  public APIResponse<ArticleResponse> updateArticleById(
      Long articleId, ArticleRequest articleRequest) {
    if (!authUtil.getCurrentUserRole().equalsIgnoreCase("AUTHOR")) {
      throw new NotFoundException("Not an Author");
    }

    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new NotFoundException("Article not found with id: " + articleId));

    article.setTitle(articleRequest.getTitle());
    article.setDescription(articleRequest.getDescription());

    if (articleRequest.getCategoryIds() != null) {
      if (articleRequest.getCategoryIds().isEmpty()) {
        throw new BadRequestException("At least one category is required");
      }

      List<Long> requestedIds = articleRequest.getCategoryIds();
      List<Category> categories = categoryRepository.findAllById(requestedIds);

      List<Long> foundIds = categories.stream().map(Category::getId).toList();
      List<Long> missingIds = requestedIds.stream().filter(id -> !foundIds.contains(id)).toList();

      if (!missingIds.isEmpty()) {
        throw new NotFoundException("Categories not found: " + missingIds);
      }

      article.getArticleCategories().clear();

      for (Category category : categories) {
        CategoryArticleId id = new CategoryArticleId(article.getArticleId(), category.getId());
        CategoryArticle categoryArticle =
            CategoryArticle.builder().id(id).article(article).category(category).build();
        article.getArticleCategories().add(categoryArticle);
      }
    }

    Article updatedArticle = articleRepository.saveAndFlush(article);
    ArticleResponse response = ArticleResponse.fromArticle(updatedArticle);

    return new APIResponse<>(
        "Article updated successfully", response, HttpStatus.OK, LocalDateTime.now());
  }
}
