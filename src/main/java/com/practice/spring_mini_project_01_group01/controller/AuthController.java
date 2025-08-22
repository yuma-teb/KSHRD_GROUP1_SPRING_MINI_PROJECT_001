package com.practice.spring_mini_project_01_group01.controller;

import com.practice.spring_mini_project_01_group01.dto.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public class AuthController {

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserCreateRequest request) {
    return ResponseEntity.ok(request);
  }
}
