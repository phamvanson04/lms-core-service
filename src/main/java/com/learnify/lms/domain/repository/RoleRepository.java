package com.learnify.lms.domain.repository;

import com.learnify.lms.domain.model.Role;
import java.util.Optional;

public interface RoleRepository {
  Optional<Role> findByRoleName(String roleName);
}
