package com.practice.spring_mini_project_01_group01.repository;

import com.practice.spring_mini_project_01_group01.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
