package com.practice.spring_mini_project_01_group01.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse<T> {
  private Timestamp timestamp;
  private boolean success;
  private String message;
  private T payload;
}
