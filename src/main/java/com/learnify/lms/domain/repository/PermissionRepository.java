package com.learnify.lms.domain.repository;

import com.learnify.lms.domain.model.Permission;
import java.util.Optional;

public interface PermissionRepository {
  Optional<Permission> findByPermissionCode(String permissionCode);
}
