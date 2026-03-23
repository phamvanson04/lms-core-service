package com.learnify.lms.domain.repository;

import com.learnify.lms.domain.model.Permission;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPermissionRepository extends JpaRepository<Permission, UUID> {
  Optional<Permission> findByPermissionCode(String permissionCode);
}
