package com.practice.spring_mini_project_01_group01.dto.user;

import com.practice.spring_mini_project_01_group01.model.User;

public class UserMapper {

  public static UserResponse toUserDto(User user) {
    return UserResponse.builder()
        .userId(user.getId())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .role(user.getRole())
        .address(user.getAddress())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
