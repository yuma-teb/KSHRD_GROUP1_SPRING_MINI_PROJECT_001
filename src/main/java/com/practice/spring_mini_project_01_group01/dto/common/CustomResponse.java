package com.practice.spring_mini_project_01_group01.dto.common;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
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
