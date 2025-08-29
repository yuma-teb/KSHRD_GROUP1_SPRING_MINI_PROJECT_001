package com.practice.spring_mini_project_01_group01.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    @NotBlank(message = "Phone number cannot be empty.")
        @Pattern(
            regexp = "^(0\\d{8,9}|\\+855\\d{8,9})$",
            message =
                "Invalid phone number format. Must start with 0 or +855 and follow by 8-9 characters long")
        @Size(min = 9, max = 15, message = "Phone number must be between 9 and 15 digits.")
        String phoneNumber,
    @NotBlank(message = "Address cannot be empty.")
        @Size(max = 255, message = "Address cannot be longer than 255 characters.")
        String address) {}
