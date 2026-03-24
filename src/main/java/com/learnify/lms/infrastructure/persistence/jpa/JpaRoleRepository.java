package com.learnify.lms.infrastructure.persistence.jpa;

import com.learnify.lms.domain.model.Role;
import com.learnify.lms.domain.repository.RoleRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends JpaRepository<Role, UUID>, RoleRepository {}
