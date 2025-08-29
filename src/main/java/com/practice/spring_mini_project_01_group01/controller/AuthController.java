package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.dto.auth.AuthResponse;
import com.practice.spring_mini_project_01_group01.dto.auth.LoginRequest;
import com.practice.spring_mini_project_01_group01.dto.auth.RefreshTokenRequest;
import com.practice.spring_mini_project_01_group01.dto.common.CustomResponse;
import com.practice.spring_mini_project_01_group01.dto.user.UserCreationRequest;
import com.practice.spring_mini_project_01_group01.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  @Operation(summary = "Register a new user")
  public ResponseEntity<CustomResponse<?>> register(
      @Valid @RequestBody UserCreationRequest request) {
    CustomResponse<Object> response =
        CustomResponse.builder()
            .payload(authService.register(request))
            .message("User registered successfully")
            .success(true)
            .build();

    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  @Operation(summary = "Login with email and password")
  public ResponseEntity<CustomResponse<AuthResponse>> login(
      @Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Refresh new token")
  public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
  }
}
