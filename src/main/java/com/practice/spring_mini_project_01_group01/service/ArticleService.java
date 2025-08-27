package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import java.util.List;

public interface ArticleService {
  ArticleResponse save(ArticleRequest articleRequest);

  List<ArticleResponse> findAll();
}
