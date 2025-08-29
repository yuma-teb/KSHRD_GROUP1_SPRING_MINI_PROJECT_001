package com.practice.spring_mini_project_01_group01.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practice.spring_mini_project_01_group01.dto.article.ArticleResponse;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_id")
  private Long articleId;

  @Column(nullable = false)
  private String title;

  private String description;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<CategoryArticle> articleCategories = new ArrayList<>();

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Comment> comments = new ArrayList<>();

  public List<String> getCategoryNames() {
    return this.articleCategories.stream()
        .map(categoryArticle -> categoryArticle.getCategory().getCategoryName())
        .toList();
  }

  public ArticleResponse getArticleResponse() {
    return new ArticleResponse(
        this.articleId,
        this.title,
        this.description,
        this.user.getId(),
        getCategoryNames(),
        this.createdAt,
        this.updatedAt);
  }
}
