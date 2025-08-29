package com.practice.spring_mini_project_01_group01.repository;

import com.practice.spring_mini_project_01_group01.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  User findUserByEmail(String username);
}
