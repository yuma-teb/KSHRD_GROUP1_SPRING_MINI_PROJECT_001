package com.practice.spring_mini_project_01_group01.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

  public static UserDetails getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      return (UserDetails) authentication.getPrincipal();
    }

    return null;
  }
}
