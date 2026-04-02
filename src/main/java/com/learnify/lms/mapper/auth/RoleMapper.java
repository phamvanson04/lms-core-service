package com.learnify.lms.mapper.auth;

import com.learnify.lms.model.Role;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  @Named("primaryRole")
  default String primaryRole(Set<Role> roles) {
    if (roles == null || roles.isEmpty()) {
      return null;
    }
    return roles.stream().map(Role::getRoleName).sorted().findFirst().orElse(null);
  }
}
