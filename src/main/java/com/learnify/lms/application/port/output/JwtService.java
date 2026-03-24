package com.learnify.lms.application.port.output;

import com.learnify.lms.domain.model.User;
import org.springframework.http.ResponseCookie;

public interface JwtService {
  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  String extractSubject(String token);

  boolean isAccessToken(String token);

  boolean isRefreshToken(String token);

  ResponseCookie createCookie(String name, String value, long maxAgeInMs);

  long getAccessTokenExpirationMs();

  long getRefreshTokenExpirationMs();
}
