package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.common.enums.ArticleProperty;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
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
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;

  @Transactional
  @Override
  public ArticleResponse save(ArticleRequest articleRequest) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Article article =
        Article.builder()
            .title(articleRequest.getTitle())
            .description(articleRequest.getDescription())
            .user(currentUser)
            .build();

    article.setArticleCategories(new ArrayList<>());

    Article savedArticle = articleRepository.save(article);

    if (articleRequest.getCategoryIds() != null && !articleRequest.getCategoryIds().isEmpty()) {
      List<Category> categories = categoryRepository.findAllById(articleRequest.getCategoryIds());
      categories.forEach(
          category -> {
            CategoryArticleId id =
                new CategoryArticleId(savedArticle.getArticleId(), category.getId());

            CategoryArticle categoryArticle =
                CategoryArticle.builder().id(id).article(savedArticle).category(category).build();

            savedArticle.getArticleCategories().add(categoryArticle);
          });
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
}
