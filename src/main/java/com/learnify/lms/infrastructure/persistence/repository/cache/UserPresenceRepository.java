package com.learnify.lms.infrastructure.persistence.repository.cache;

import java.time.Duration;
import java.util.UUID;

public interface UserPresenceRepository {
  void setOnline(UUID userID, Duration ttl);

  void refreshTtl(UUID userID, Duration ttl);

  void deleteOnline(UUID userID);

  boolean isOnline(UUID userID);
}

