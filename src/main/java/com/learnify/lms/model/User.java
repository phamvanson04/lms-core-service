package com.learnify.lms.model;

import jakarta.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

@Entity
@Table(
    name = "users",
    indexes = {
      @Index(name = "idx_users_email", columnList = "email"),
      @Index(name = "idx_users_username", columnList = "username"),
      @Index(name = "idx_users_created_at", columnList = "created_at")
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "deleted_at IS NULL")
public class User extends AbstractEntity {
  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "full_name", nullable = false, length = 100)
  private String fullName;

  @Column(length = 20, unique = true)
  private String phone;

  @Column(length = 255)
  private String address;

  @Column(columnDefinition = "TEXT")
  private String bio;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Builder.Default
  private Set<Role> roles = new HashSet<>();

  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
  private Set<Course> taughtCourses;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<Avatar> avatars = new HashSet<>();

  // method helper
  public void addRole(Role role) {
    if (role != null) {
      this.roles.add(role);
      role.getUsers().add(this);
    }
  }

  public void removeRole(Role role) {
    if (role != null) {
      this.roles.remove(role);
      role.getUsers().remove(this);
    }
  }

  public String getAvatarUrl() {
    return avatars == null
        ? null
        : avatars.stream()
            .max(
                Comparator.comparing(
                    Avatar::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())))
            .map(Avatar::getUrl)
            .orElse(null);
  }

  public void setAvatarUrl(String avatarUrl) {
    if (avatars == null) {
      avatars = new HashSet<>();
    }
    avatars.clear();
    if (StringUtils.hasText(avatarUrl)) {
      Avatar avatar = Avatar.builder().url(avatarUrl).user(this).build();
      avatars.add(avatar);
    }
  }
}
