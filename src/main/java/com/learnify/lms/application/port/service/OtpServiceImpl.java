package com.learnify.lms.application.port.service;

import com.learnify.lms.application.dto.response.auth.OtpMeta;
import com.learnify.lms.application.port.IOtpService;
import com.learnify.lms.common.exception.AppException;
import com.learnify.lms.common.exception.ErrorCode;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements IOtpService {
  private final RedisTemplate<String, String> redisTemplate;
  private static final String OTP_PREFIX = "otp:";
  private static final int OTP_LENGTH = 6;
  private static final long OTP_TTL_SECOND = 60;
  private static final long ATTEMPT_WINDOW_SECONDS = 20 * 60;
  private static final int MAX_ATTEMPT = 5;
  private static final String OTP_ATTEMPT_PREFIX = "otp:attempt:";
  private static final SecureRandom secureRandom = new SecureRandom();

  @Override
  public OtpMeta generateOtp(String email) {
    String attemptKey = OTP_ATTEMPT_PREFIX + email;
    Long attempt = redisTemplate.opsForValue().increment(attemptKey);
    if (attempt != null && attempt == 1) {
      redisTemplate.expire(attemptKey, ATTEMPT_WINDOW_SECONDS, TimeUnit.SECONDS);
    }

    if (attempt != null && attempt > MAX_ATTEMPT) {
      throw new AppException(ErrorCode.OTP_INVALID);
    }
    int remainingAttempts = MAX_ATTEMPT - attempt.intValue();
    String otp = createOtp();
    String otpKey = OTP_PREFIX + email;
    redisTemplate.opsForValue().set(otpKey, otp, OTP_TTL_SECOND, TimeUnit.SECONDS);
    Long ttl = redisTemplate.getExpire(otpKey, TimeUnit.SECONDS);
    int otpTtlSeconds = (ttl != null && ttl > 0) ? ttl.intValue() : 0;
    return new OtpMeta(otpTtlSeconds, remainingAttempts);
  }

  @Override
  public boolean verifyOtp(String email, String otp) {
    String key = OTP_PREFIX + email;
    String storedOtp = redisTemplate.opsForValue().get(key);
    if (storedOtp != null && storedOtp.equals(otp)) {
      redisTemplate.delete(key);
      return true;
    }
    return false;
  }

  @Override
  public void invalidateOtp(String email) {
    String key = OTP_PREFIX + email;
    redisTemplate.delete(key);
  }

  private String createOtp() {
    StringBuilder otp = new StringBuilder();
    for (int i = 0; i < OTP_LENGTH; i++) {
      otp.append(secureRandom.nextInt(10));
    }
    return otp.toString();
  }
}
