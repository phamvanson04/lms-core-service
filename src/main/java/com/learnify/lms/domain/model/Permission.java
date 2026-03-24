package com.learnify.lms.domain.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @EqualsAndHashCode.Include
  protected UUID id;

  @Column(nullable = false, unique = true)
  private String permissionCode;

  @ManyToMany(mappedBy = "permissions")
  private Set<Role> roles = new HashSet<>();
}
