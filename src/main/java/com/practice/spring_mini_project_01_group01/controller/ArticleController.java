package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.common.enums.ArticleProperty;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentRequest;
import com.practice.spring_mini_project_01_group01.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {

  private final ArticleService articleService;

  // Create article
  @PostMapping
  public ArticleResponse createArticle(@RequestBody ArticleRequest articleRequest) {
    ArticleResponse savedArticle = articleService.save(articleRequest);
    return savedArticle;
  }

  // Get Article
  @GetMapping
  public APIResponse<List<ArticleResponse>> getAllArticles(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam ArticleProperty articleProperty,
      @RequestParam Sort.Direction direction) {
    return articleService.findAll(page, size, articleProperty, direction);
  }

  // Create comment specific article
  @PostMapping("/{articleId}/comments")
  public APIResponse<ArticleResponse> createComment(
      @PathVariable("articleId") Long articleId, @RequestBody CommentRequest commentRequest) {
    return articleService.addComment(articleId, commentRequest);
  }

  // Delete article
  @DeleteMapping("/{articleId}")
  public APIResponse<Void> deleteArticle(@PathVariable("articleId") Long articleId) {
    return articleService.deteleArticle(articleId);
  }

  // Get article by id
  @GetMapping("/{articleId}")
  public APIResponse<ArticleResponse> getArticleById(@PathVariable("articleId") Long articleId) {
    return articleService.getArticleById(articleId);
  }
}
