package com.practice.spring_mini_project_01_group01.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T payload;

  private HttpStatus status;
  private LocalDateTime instant;
}
