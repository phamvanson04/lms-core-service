package com.learnify.lms.service;

import static com.learnify.lms.constants.AuthConstants.ROLE_STUDENT;

import com.learnify.lms.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.dto.request.auth.LoginRequest;
import com.learnify.lms.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.dto.response.auth.RegistrationResponse;
import com.learnify.lms.mapper.auth.AuthenticationMapper;
import com.learnify.lms.mapper.auth.CreateAccountMapper;
import com.learnify.lms.mapper.auth.RegistrationMapper;
import com.learnify.lms.service.interfaces.IAuthService;
import com.learnify.lms.exception.AppException;
import com.learnify.lms.exception.AuthException;
import com.learnify.lms.exception.ErrorCode;
import com.learnify.lms.service.interfaces.IJwtService;
import com.learnify.lms.util.UserHelper;
import com.learnify.lms.util.ValidateHelper;
import com.learnify.lms.model.Role;
import com.learnify.lms.model.User;
import com.learnify.lms.repository.JpaRoleRepository;
import com.learnify.lms.repository.JpaUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
  private final JpaUserRepository usersRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final IJwtService IJwtService;
  private final CreateAccountMapper createAccountMapper;
  private final RegistrationMapper registrationMapper;
  private final AuthenticationMapper authenticationMapper;

  @Override
  public RegistrationResponse register(CreateAccountRequest request) {
    if (!ValidateHelper.validateEmail(request.getEmail())) {
      throw new AppException(ErrorCode.INVALID_EMAIL_FORMAT);
    }
    if (!ValidateHelper.isStrongPassword(request.getPassword())) {
      throw new AppException(ErrorCode.PASSWORD_MISSING_SPECIAL_CHAR);
    }
    if (usersRepository.existsByEmail(request.getEmail())) {
      throw new AuthException(ErrorCode.EMAIL_EXISTS);
    }
    if (request.getFullName() == null || request.getFullName().isBlank()) {
      throw new AppException(ErrorCode.FULLNAME_NOT_EMPTY);
    }
    if (StringUtils.isEmpty(request.getPhone())
        || !ValidateHelper.isValidPhoneVN(request.getPhone())) {
      throw new AppException(ErrorCode.INVALID_PHONE_NUMBER_VN);
    }
    if (usersRepository.existsByPhone(request.getPhone())) {
      throw new AppException(ErrorCode.PHONE_EXISTS);
    }

    String username = UserHelper.generateUniqueUsername(request.getFullName(), usersRepository);
    Role studentRole =
        jpaRoleRepository
            .findByRoleName(ROLE_STUDENT)
            .orElseThrow(() -> new AuthException(ErrorCode.ROLE_NOT_FOUND));
    Set<Role> roles = new HashSet<>();
    roles.add(studentRole);
    User user = createAccountMapper.toEntity(request);
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRoles(roles);

    User saved = usersRepository.save(user);

    return registrationMapper.toDto(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public AuthenticationResponse login(LoginRequest request, HttpServletResponse response) {
    if (!ValidateHelper.validateEmail(request.getEmail())) {
      throw new AuthException(ErrorCode.INVALID_EMAIL_FORMAT);
    }

    User user =
        usersRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new AuthException(ErrorCode.INVALID_CREDENTIALS));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new AppException(ErrorCode.INVALID_CREDENTIALS);
    }

    String accessToken = IJwtService.generateAccessToken(user);
    String refreshToken = IJwtService.generateRefreshToken(user);

    ResponseCookie accessTokenCookie =
        IJwtService.createCookie(
            "access_token", accessToken, IJwtService.getAccessTokenExpirationMs());
    ResponseCookie refreshTokenCookie =
        IJwtService.createCookie(
            "refresh_token", refreshToken, IJwtService.getRefreshTokenExpirationMs());
    response.addHeader("Set-Cookie", accessTokenCookie.toString());
    response.addHeader("Set-Cookie", refreshTokenCookie.toString());

    return authenticationMapper.toDto(user);
  }

  @Override
  @Transactional(readOnly = true)
  public AuthenticationResponse refreshToken(
      HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = extractRefreshToken(request);
    if (refreshToken == null) {
      throw new AuthException(ErrorCode.INVALID_TOKEN);
    }

    try {
      if (!IJwtService.isRefreshToken(refreshToken)) {
        throw new AuthException(ErrorCode.INVALID_TOKEN);
      }
      String userId = IJwtService.extractSubject(refreshToken);

      User user =
          usersRepository
              .findById(UUID.fromString(userId))
              .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

      String newAccessToken = IJwtService.generateAccessToken(user);
      String newRefreshToken = IJwtService.generateRefreshToken(user);

      ResponseCookie accessTokenCookie =
          IJwtService.createCookie(
              "access_token", newAccessToken, IJwtService.getAccessTokenExpirationMs());
      ResponseCookie refreshTokenCookie =
          IJwtService.createCookie(
              "refresh_token", newRefreshToken, IJwtService.getRefreshTokenExpirationMs());
      response.addHeader("Set-Cookie", accessTokenCookie.toString());
      response.addHeader("Set-Cookie", refreshTokenCookie.toString());

      return authenticationMapper.toDto(user);
    } catch (RuntimeException e) {
      throw new AuthException(ErrorCode.INVALID_TOKEN);
    }
  }

  private String extractRefreshToken(HttpServletRequest request) {
    if (request.getCookies() == null) {
      return null;
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> "refresh_token".equals(cookie.getName()))
        .map(cookie -> cookie.getValue())
        .findFirst()
        .orElse(null);
  }
}
