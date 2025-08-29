package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.common.enums.ArticleProperty;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentRequest;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface ArticleService {
  ArticleResponse save(ArticleRequest articleRequest);

  APIResponse<List<ArticleResponse>> findAll(
      int page, int size, ArticleProperty articleProperty, Sort.Direction direction);

  ArticleResponse addComment(Long articleId, CommentRequest commentRequest);

  APIResponse<Void> deteleArticle(Long articleId);

  APIResponse<ArticleResponse> getArticleById(Long articleId);

  APIResponse<ArticleResponse> updateArticleById(Long articleId, ArticleRequest articleRequest);
}
