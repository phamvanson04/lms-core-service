package com.learnify.lms.application.service.jwt;

import com.learnify.lms.domain.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseCookie;

public interface IJwtService {
  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  Claims extractAllClaims(String token);

  ResponseCookie createCookie(String name, String value, long maxAgeInMs);

  long getAccessTokenExpirationMs();

  long getRefreshTokenExpirationMs();
}

