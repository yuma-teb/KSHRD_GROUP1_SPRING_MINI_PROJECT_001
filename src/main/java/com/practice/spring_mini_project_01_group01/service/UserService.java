package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.user.UserResponse;
import com.practice.spring_mini_project_01_group01.dto.user.UserUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  UserResponse getCurrentUser();

  UserResponse updateCurrentUser(@Valid UserUpdateRequest req);
}
