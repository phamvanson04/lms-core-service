package com.learnify.lms.domain.model;

import com.learnify.lms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Column(length = 500)
  private String description;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(nullable = false)
  @Builder.Default
  private Boolean active = true;
}

