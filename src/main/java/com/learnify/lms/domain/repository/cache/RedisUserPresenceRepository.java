package com.learnify.lms.domain.repository.cache;

import java.time.Duration;
import java.util.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisUserPresenceRepository implements UserPresenceRepository {
  private static final String KEY_PREFIX = "online:user:";
  private static final String ONLINE_VALUE = "1";

  private final StringRedisTemplate stringRedisTemplate;

  public RedisUserPresenceRepository(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public void setOnline(UUID userID, Duration ttl) {
    stringRedisTemplate.opsForValue().set(key(userID), ONLINE_VALUE, ttl);
  }

  @Override
  public void refreshTtl(UUID userID, Duration ttl) {
    stringRedisTemplate.expire(key(userID), ttl);
  }

  @Override
  public void deleteOnline(UUID userID) {
    stringRedisTemplate.delete(key(userID));
  }

  @Override
  public boolean isOnline(UUID userID) {
    return stringRedisTemplate.hasKey(key(userID));
  }

  private String key(UUID userID) {
    return KEY_PREFIX + userID.toString();
  }
}
