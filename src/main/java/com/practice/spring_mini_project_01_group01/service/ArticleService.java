package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.model.Article;

public interface ArticleService {
  Article commentArticle(Long articleId, String content);
}
