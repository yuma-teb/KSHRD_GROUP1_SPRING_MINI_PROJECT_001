package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.dto.auth.AuthResponse;
import com.practice.spring_mini_project_01_group01.dto.auth.LoginRequest;
import com.practice.spring_mini_project_01_group01.dto.user.UserCreationRequest;
import com.practice.spring_mini_project_01_group01.model.User;
import com.practice.spring_mini_project_01_group01.repository.UserRepository;
import com.practice.spring_mini_project_01_group01.security.JwtService;
import com.practice.spring_mini_project_01_group01.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthResponse register(UserCreationRequest request) {
    User user =
        User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phoneNumber(request.getPhoneNumber())
            .address(request.getAddress())
            .role(request.getRole())
            .build();
    userRepository.save(user);

    String accessToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  @Override
  public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    User user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    String accessToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  @Override
  public AuthResponse refreshToken(String refreshToken) {
    String userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      User user =
          this.userRepository
              .findByEmail(userEmail)
              .orElseThrow(() -> new RuntimeException("User not found"));
      if (jwtService.isTokenValid(refreshToken, user)) {
        String accessToken = jwtService.generateToken(user);
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
      }
    }
    throw new RuntimeException("Refresh token is not valid");
  }
}
