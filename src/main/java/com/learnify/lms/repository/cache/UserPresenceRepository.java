package com.learnify.lms.repository.cache;

import java.time.Duration;
import java.util.UUID;

public interface UserPresenceRepository {
  void setOnline(UUID userId, Duration ttl);

  void refreshTtl(UUID userId, Duration ttl);

  void deleteOnline(UUID userId);

  boolean isOnline(UUID userId);
}
