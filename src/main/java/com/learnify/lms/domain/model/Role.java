package com.learnify.lms.domain.model;

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
  private Set<Permissions> permissions = new HashSet<>();

  public void addPermission(Permissions permissions) {
    if (permissions != null && !this.permissions.contains(permissions)) {
      this.permissions.add(permissions);
      permissions.getRoles().add(this);
    }
  }

  public void removePermission(Permissions permissions) {
    if (permissions == null) {
      return;
    }
    if (this.permissions.contains(permissions)) {
      this.permissions.remove(permissions);
      permissions.getRoles().remove(this);
    }
  }
}

