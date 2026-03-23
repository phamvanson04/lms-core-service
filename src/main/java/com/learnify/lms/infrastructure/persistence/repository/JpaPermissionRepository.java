package com.learnify.lms.infrastructure.persistence.repository;

import com.learnify.lms.domain.model.Permissions;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPermissionRepository extends JpaRepository<Permissions, UUID> {
  Optional<Permissions> findByPermissionCode(String permissionCode);
}

