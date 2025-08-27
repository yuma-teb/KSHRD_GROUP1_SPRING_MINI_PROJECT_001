package com.practice.spring_mini_project_01_group01.dto.user;

import com.practice.spring_mini_project_01_group01.common.enums.UserRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
  private Long userId;
  private String email;
  private String phoneNumber;
  private UserRole role;
  private String address;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
