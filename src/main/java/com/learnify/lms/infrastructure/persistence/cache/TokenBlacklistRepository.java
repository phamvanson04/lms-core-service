package com.learnify.lms.infrastructure.persistence.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenBlacklistRepository {
  private final StringRedisTemplate stringRedisTemplate;

  public TokenBlacklistRepository(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }
}
