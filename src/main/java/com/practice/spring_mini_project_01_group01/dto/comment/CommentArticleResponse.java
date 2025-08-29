package com.practice.spring_mini_project_01_group01.dto.comment;

import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentArticleResponse {
  private ArticleResponse articleResponse;
  private List<CommentResponse> comments;
}
