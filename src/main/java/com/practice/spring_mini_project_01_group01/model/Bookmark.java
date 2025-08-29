package com.practice.spring_mini_project_01_group01.model;

import com.practice.spring_mini_project_01_group01.base.BaseEntity;
import com.practice.spring_mini_project_01_group01.dto.bookmark.BookmarkResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookmarks")
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public BookmarkResponse toBookmarkResponse() {
        return BookmarkResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .description(article.getDescription())
                .userId(user.getId())
                .categories(article.getArticleCategories().stream().map(ca -> ca.getCategory().getCategoryName()).toList())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
