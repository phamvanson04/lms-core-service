package com.learnify.lms.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @EqualsAndHashCode.Include
  protected UUID id;

  @Column(nullable = false, unique = true, length = 20)
  private String roleName;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "role_permissions",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions = new HashSet<>();

  public void addPermission(Permission permission) {
    if (permission != null && !this.permissions.contains(permission)) {
      this.permissions.add(permission);
      permission.getRoles().add(this);
    }
  }

  public void removePermission(Permission permission) {
    if (permission == null) {
      return;
    }
    if (this.permissions.contains(permission)) {
      this.permissions.remove(permission);
      permission.getRoles().remove(this);
    }
  }
}
