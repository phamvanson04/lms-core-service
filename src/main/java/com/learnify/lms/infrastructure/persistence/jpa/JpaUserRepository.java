package com.learnify.lms.infrastructure.persistence.jpa;

import com.learnify.lms.domain.model.User;
import com.learnify.lms.domain.repository.UserRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID>, UserRepository {}
