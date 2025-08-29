package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.model.Article;
import com.practice.spring_mini_project_01_group01.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
  private final ArticleService articleService;

  @PostMapping("/{articleId}")
  public Article commentArticle(@PathVariable("articleId") Long articleId, String content) {
    return articleService.commentArticle(articleId, content);
  }
}
