package com.practice.spring_mini_project_01_group01.dto.commet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
  private String content;

  public CommentResponse toResponse() {
    return new CommentResponse(null, this.content, null, null, null);
  }
}
