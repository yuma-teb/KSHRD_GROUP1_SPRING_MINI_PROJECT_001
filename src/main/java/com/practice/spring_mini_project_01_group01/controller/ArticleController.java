package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import com.practice.spring_mini_project_01_group01.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public List<ArticleResponse> getAllArticles() {
    return articleService.findAll();
  }
}
