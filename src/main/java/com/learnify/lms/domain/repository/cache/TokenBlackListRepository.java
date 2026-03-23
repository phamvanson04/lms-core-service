package com.learnify.lms.domain.repository.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenBlackListRepository {
  private final StringRedisTemplate stringRedisTemplate;

  public TokenBlackListRepository(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }
}
