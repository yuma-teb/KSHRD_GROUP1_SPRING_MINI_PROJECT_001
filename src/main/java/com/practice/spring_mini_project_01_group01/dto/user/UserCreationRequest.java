package com.practice.spring_mini_project_01_group01.dto.user;

import com.practice.spring_mini_project_01_group01.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationRequest {

  @NotBlank(message = "Username cannot be empty.")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
  private String username;

  @NotBlank(message = "Email cannot be empty.")
  @Email(message = "Please provide a valid email address.")
  private String email;

  @NotBlank(message = "Password cannot be empty.")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
      message =
          "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.")
  private String password;

  @NotBlank(message = "Phone number cannot be empty.")
  @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits.")
  @Size(min = 9, max = 15, message = "Phone number must be between 9 and 15 digits.")
  private String phoneNumber;

  @NotBlank(message = "Address cannot be empty.")
  @Size(max = 255, message = "Address cannot be longer than 255 characters.")
  private String address;

  private UserRole role;
}
