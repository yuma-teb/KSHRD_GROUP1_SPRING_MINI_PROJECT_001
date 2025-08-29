package com.practice.spring_mini_project_01_group01.dto.bookmark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookmarkResponse {

    private Long articleId;
    private String title;
    private String description;
    private Long userId;
    private List<String> categories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
