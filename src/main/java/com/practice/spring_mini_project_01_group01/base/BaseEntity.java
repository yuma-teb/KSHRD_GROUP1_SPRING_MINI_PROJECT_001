package com.practice.spring_mini_project_01_group01.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt = LocalDateTime.now();
}
