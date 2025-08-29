package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.bookmark.BookmarkResponse;
import com.practice.spring_mini_project_01_group01.dto.common.CustomResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookmarkService {
    CustomResponse<BookmarkResponse> addBookmarkByArticleId(@Positive Long articleId);

    CustomResponse<List<BookmarkResponse>> getAllBookmarksByCurrentUser(int page, int size, Sort.Direction direction);

    CustomResponse<Void> deleteBookmarkByArticleId(@Positive Long articleId);
}
