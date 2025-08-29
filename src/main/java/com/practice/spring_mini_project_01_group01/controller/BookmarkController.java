package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.dto.bookmark.BookmarkResponse;
import com.practice.spring_mini_project_01_group01.dto.common.CustomResponse;
import com.practice.spring_mini_project_01_group01.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
@Tag(name = "bookmark-controller")
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {

  private final BookmarkService bookmarkService;

  @PostMapping("/{article_id}")
  @Operation(summary = "Add bookmark on article")
  public ResponseEntity<CustomResponse<BookmarkResponse>> addBookmarkByArticleId(
      @PathVariable("article_id") @Positive Long article_id) {
    return ResponseEntity.ok().body(bookmarkService.addBookmarkByArticleId(article_id));
  }

  @GetMapping
  @Operation(summary = "Get all articles which has added bookmark by current user id")
  public ResponseEntity<CustomResponse<List<BookmarkResponse>>> getAllBookmarksByCurrentUser(
      @RequestParam(defaultValue = "1") @Positive int page,
      @RequestParam(defaultValue = "10") @Positive int size,
      Sort.Direction direction) {
    return ResponseEntity.ok()
        .body(bookmarkService.getAllBookmarksByCurrentUser(page, size, direction));
  }

  @DeleteMapping("/{article_id}")
  @Operation(summary = "Delete bookmark on article")
  public ResponseEntity<CustomResponse<Void>> deleteBookmarkByArticleId(
      @PathVariable("article_id") @Positive Long article_id) {
    ;
    return ResponseEntity.ok().body(bookmarkService.deleteBookmarkByArticleId(article_id));
  }
}
