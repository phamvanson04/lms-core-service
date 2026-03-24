package com.learnify.lms.infrastructure.persistence.jpa;

import com.learnify.lms.domain.model.Permission;
import com.learnify.lms.domain.repository.PermissionRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPermissionRepository
    extends JpaRepository<Permission, UUID>, PermissionRepository {}
