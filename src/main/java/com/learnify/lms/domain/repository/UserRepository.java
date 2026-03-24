package com.learnify.lms.domain.repository;

import com.learnify.lms.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  Optional<User> findByEmail(String email);

  Optional<User> findById(UUID id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhone(String phone);

  boolean existsByPhoneAndIdNot(String phone, UUID id);

  User save(User user);
}
