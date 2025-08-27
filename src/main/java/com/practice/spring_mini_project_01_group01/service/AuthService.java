package com.practice.spring_mini_project_01_group01.service;

import com.practice.spring_mini_project_01_group01.dto.auth.AuthResponse;
import com.practice.spring_mini_project_01_group01.dto.auth.LoginRequest;
import com.practice.spring_mini_project_01_group01.dto.user.UserCreationRequest;

public interface AuthService {
  AuthResponse register(UserCreationRequest request);

  AuthResponse login(LoginRequest request);

  AuthResponse refreshToken(String refreshToken);
}
