package com.learnify.lms.domain.repository;

import com.learnify.lms.domain.model.Role;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByRoleName(String roleName);
}
