package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.dto.user.UserMapper;
import com.practice.spring_mini_project_01_group01.dto.user.UserResponse;
import com.practice.spring_mini_project_01_group01.dto.user.UserUpdateRequest;
import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.User;
import com.practice.spring_mini_project_01_group01.repository.UserRepository;
import com.practice.spring_mini_project_01_group01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public User currentUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }

  public UserResponse getCurrentUser() {
    return UserMapper.toUserDto(
        userRepository
            .findById(currentUser().getId())
            .orElseThrow(
                () -> new NotFoundException("User not found with id: " + currentUser().getId())));
  }

  @Override
  public UserResponse updateCurrentUser(UserUpdateRequest req) {
    User currentUser =
        userRepository
            .findById(currentUser().getId())
            .orElseThrow(
                () -> new NotFoundException("User not found with id: " + currentUser().getId()));

    currentUser.setPhoneNumber(req.phoneNumber());
    currentUser.setAddress(req.address());
    return UserMapper.toUserDto(userRepository.save(currentUser));
  }
}
