package com.practice.spring_mini_project_01_group01;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT")
public class SpringMiniProject01Group01Application {

  public static void main(String[] args) {
    SpringApplication.run(SpringMiniProject01Group01Application.class, args);
  }
}
