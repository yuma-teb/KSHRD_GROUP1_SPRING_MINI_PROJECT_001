package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.common.utils.AuthUtil;
import com.practice.spring_mini_project_01_group01.dto.bookmark.BookmarkResponse;
import com.practice.spring_mini_project_01_group01.dto.common.CustomResponse;
import com.practice.spring_mini_project_01_group01.exception.BadRequestException;
import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.Article;
import com.practice.spring_mini_project_01_group01.model.Bookmark;
import com.practice.spring_mini_project_01_group01.model.User;
import com.practice.spring_mini_project_01_group01.repository.ArticleRepository;
import com.practice.spring_mini_project_01_group01.repository.BookmarkRepository;
import com.practice.spring_mini_project_01_group01.service.BookmarkService;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImp implements BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final AuthUtil authUtil;
  private final ArticleRepository articleRepository;

  @Override
  public CustomResponse<BookmarkResponse> addBookmarkByArticleId(Long articleId) {

    Article article =
        articleRepository
            .findById(articleId)
            .orElseThrow(() -> new NotFoundException("Article " + articleId + " Not Found"));

    User user = authUtil.getCurrentUser();

    bookmarkRepository
        .findByArticle_ArticleIdAndUser_Id(articleId, user.getId())
        .ifPresent(
            b -> {
              throw new BadRequestException("Already bookmarked");
            });

    Bookmark bookmark =
        bookmarkRepository.save(Bookmark.builder().article(article).user(user).build());

    return CustomResponse.<BookmarkResponse>builder()
        .timestamp(Timestamp.valueOf(LocalDateTime.now()))
        .success(true)
        .message("Bookmark added successfully")
        .payload(bookmark.toBookmarkResponse())
        .build();
  }

  @Override
  public CustomResponse<List<BookmarkResponse>> getAllBookmarksByCurrentUser(
      int page, int size, Sort.Direction direction) {
    User user = authUtil.getCurrentUser();

    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, "createdAt"));
    Page<Bookmark> bookmarkPage = bookmarkRepository.findByUserId(user.getId(), pageable);

    List<BookmarkResponse> payload =
        bookmarkPage.stream().map(Bookmark::toBookmarkResponse).toList();

    return CustomResponse.<List<BookmarkResponse>>builder()
        .timestamp(Timestamp.valueOf(LocalDateTime.now()))
        .success(true)
        .message("Bookmark added successfully")
        .payload(payload)
        .build();
  }

  @Override
  @Transactional
  public CustomResponse<Void> deleteBookmarkByArticleId(Long articleId) {
    if (articleId == null) {
      throw new BadRequestException("articleId must not be null");
    }

    User user = authUtil.getCurrentUser();

    long deleted = bookmarkRepository.deleteByArticle_ArticleIdAndUser_Id(articleId, user.getId());
    if (deleted == 0L) {
      throw new NotFoundException("Bookmark for Article ID " + articleId + " not found");
    }

    return CustomResponse.<Void>builder()
        .timestamp(Timestamp.valueOf(LocalDateTime.now()))
        .success(true)
        .message("Bookmark deleted successfully")
        .payload(null)
        .build();
  }
}
