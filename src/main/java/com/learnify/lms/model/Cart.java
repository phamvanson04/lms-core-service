package com.learnify.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends AbstractEntity {
  @OneToOne
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private User user;
}
