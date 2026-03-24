package com.learnify.lms.infrastructure.security;

import com.learnify.lms.application.port.output.JwtService;
import com.learnify.lms.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.accessTokenExpirationMs}")
  private long accessTokenExpirationMs;

  @Value("${jwt.refreshTokenExpirationMs}")
  private long refreshTokenExpirationMs;

  private static final String TYPE = "type";
  private static final String ACCESS_TYPE = "access";
  private static final String REFRESH_TYPE = "refresh";

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public String generateAccessToken(User user) {
    long now = System.currentTimeMillis();
    String jti = UUID.randomUUID().toString();

    var roleNames = user.getRoles().stream().map(role -> role.getRoleName()).toList();
    return Jwts.builder()
        .setSubject(user.getId().toString())
        .claim("roles", roleNames)
        .claim(TYPE, ACCESS_TYPE)
        .setId(jti)
        .issuedAt(new Date(now))
        .expiration(new Date(now + accessTokenExpirationMs))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public String generateRefreshToken(User user) {
    long now = System.currentTimeMillis();
    String jti = UUID.randomUUID().toString();

    return Jwts.builder()
        .setSubject(user.getId().toString())
        .claim(TYPE, REFRESH_TYPE)
        .setId(jti)
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + refreshTokenExpirationMs))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public String extractSubject(String token) {
    return extractAllClaims(token).getSubject();
  }

  @Override
  public boolean isAccessToken(String token) {
    return ACCESS_TYPE.equals(extractAllClaims(token).get(TYPE, String.class));
  }

  @Override
  public boolean isRefreshToken(String token) {
    return REFRESH_TYPE.equals(extractAllClaims(token).get(TYPE, String.class));
  }

  @Override
  public ResponseCookie createCookie(String name, String value, long maxAgeInMs) {
    return ResponseCookie.from(name, value)
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(maxAgeInMs / 1000)
        .sameSite("Lax")
        .build();
  }

  @Override
  public long getAccessTokenExpirationMs() {
    return this.accessTokenExpirationMs;
  }

  @Override
  public long getRefreshTokenExpirationMs() {
    return this.refreshTokenExpirationMs;
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
  }
}
