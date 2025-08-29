package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.Article;
import com.practice.spring_mini_project_01_group01.repository.ArticleRepository;
import com.practice.spring_mini_project_01_group01.service.ArticleService;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
  private final ArticleRepository articleRepository;
  private final CommentService commentService;

  @Override
  public Article commentArticle(Long articleId, String content) {
    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new NotFoundException("Article Id not found"));
    commentService.createComment(content, article);
    return article;
  }
}
