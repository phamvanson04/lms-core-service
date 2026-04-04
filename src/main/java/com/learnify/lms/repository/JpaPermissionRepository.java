package com.learnify.lms.repository;

import com.learnify.lms.model.Permission;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPermissionRepository extends JpaRepository<Permission, UUID> {
  Optional<Permission> findByPermissionCode(String permissionCode);
}
