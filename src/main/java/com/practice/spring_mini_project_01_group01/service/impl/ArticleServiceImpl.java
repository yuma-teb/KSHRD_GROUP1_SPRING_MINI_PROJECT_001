package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.dto.article.ArticleRequest;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import com.practice.spring_mini_project_01_group01.model.Article;
import com.practice.spring_mini_project_01_group01.model.User;
import com.practice.spring_mini_project_01_group01.repository.ArticleRepository;
import com.practice.spring_mini_project_01_group01.service.ArticleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;

  @Override
  public ArticleResponse save(ArticleRequest articleRequest) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Article article =
        Article.builder()
            .title(articleRequest.getTitle())
            .description(articleRequest.getDescription())
            .user(currentUser)
            .build();
    Article saved = articleRepository.save(article);
    return ArticleResponse.fromArticle(saved);
  }

  @Override
  public List<ArticleResponse> findAll() {
    List<Article> articles = articleRepository.findAll();
    return articles.stream().map(ArticleResponse::fromArticle).collect(Collectors.toList());
  }
}
