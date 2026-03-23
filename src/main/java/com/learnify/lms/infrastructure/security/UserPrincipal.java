package com.learnify.lms.infrastructure.security;

import com.learnify.lms.domain.model.User;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal
    implements UserDetails,
        Serializable { // adapter to convert our User entity to Spring Security's UserDetails
  private final UUID id;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(User user) {
    this.id = user.getId();
    this.password = user.getPassword();
    this.authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
            .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities; // Trả về cái đã có sẵn, không cần stream() lại lần nữa
  }

  @Override
  public String getUsername() {
    return id.toString();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

