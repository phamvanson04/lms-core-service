package com.learnify.lms.service.interfaces;

import com.learnify.lms.model.User;
import org.springframework.http.ResponseCookie;

public interface IJwtService {
  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  String extractSubject(String token);

  boolean isAccessToken(String token);

  boolean isRefreshToken(String token);

  ResponseCookie createCookie(String name, String value, long maxAgeInMs);

  long getAccessTokenExpirationMs();

  long getRefreshTokenExpirationMs();
}
