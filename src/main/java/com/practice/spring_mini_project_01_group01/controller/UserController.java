package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.dto.common.CustomResponse;
import com.practice.spring_mini_project_01_group01.dto.user.UserResponse;
import com.practice.spring_mini_project_01_group01.dto.user.UserUpdateRequest;
import com.practice.spring_mini_project_01_group01.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
  private final UserService userService;

  @GetMapping
  @Operation(summary = "Get current user. can be used all roles")
  public ResponseEntity<CustomResponse<UserResponse>> getCurrentUser() {
    return ResponseEntity.ok(
        CustomResponse.<UserResponse>builder()
            .timestamp(Timestamp.valueOf(LocalDateTime.now()))
            .success(true)
            .message("Get current user successfully")
            .payload(userService.getCurrentUser())
            .build());
  }

  @PutMapping
  @Operation(summary = "Update current user. can be used all roles")
  public ResponseEntity<CustomResponse<UserResponse>> updateCurrentUser(
      @RequestBody @Valid UserUpdateRequest req) {
    return ResponseEntity.ok(
        CustomResponse.<UserResponse>builder()
            .timestamp(Timestamp.valueOf(LocalDateTime.now()))
            .success(true)
            .message("Update current user successfully")
            .payload(userService.updateCurrentUser(req))
            .build());
  }
}
