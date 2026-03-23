package com.learnify.lms.domain.repository;

import com.learnify.lms.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsersRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  Optional<User> findById(UUID id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhone(String phone);

  boolean existsByPhoneAndIdNot(String phone, UUID id);
}
