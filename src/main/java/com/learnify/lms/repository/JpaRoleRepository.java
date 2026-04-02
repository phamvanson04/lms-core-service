package com.learnify.lms.repository;

import com.learnify.lms.model.Role;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByRoleName(String roleName);
}
