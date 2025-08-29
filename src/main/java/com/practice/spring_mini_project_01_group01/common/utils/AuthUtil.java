package com.practice.spring_mini_project_01_group01.common.utils;

import com.practice.spring_mini_project_01_group01.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

  public String getCurrentUserRole() {
    return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getRole()
        .name(); // if Role is an enum
  }

  public User getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User) {
      return (User) principal;
    }
    return null;
  }

  public boolean isAuthor() {
    return "Author".equals(getCurrentUserRole());
  }

  public boolean isReader() {
    return "Reader".equals(getCurrentUserRole());
  }
}
