package com.practice.spring_mini_project_01_group01.model;

import com.practice.spring_mini_project_01_group01.common.enums.UserRole;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    private String username;
    private UserRole role;
    private String password;
    private String address;
    private String phoneNumber;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
