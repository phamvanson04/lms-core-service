package com.learnify.lms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends AbstractEntity {
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
