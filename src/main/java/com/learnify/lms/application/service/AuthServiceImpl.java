package com.learnify.lms.application.service;

import static com.learnify.lms.domain.constants.AuthConstants.ROLE_STUDENT;

import com.learnify.lms.application.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.application.dto.request.auth.LoginRequest;
import com.learnify.lms.application.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.application.dto.response.auth.RegistrationResponse;
import com.learnify.lms.application.port.input.AuthService;
import com.learnify.lms.application.port.output.JwtService;
import com.learnify.lms.domain.exception.AppException;
import com.learnify.lms.domain.exception.AuthException;
import com.learnify.lms.domain.exception.ErrorCode;
import com.learnify.lms.domain.helper.UserHelper;
import com.learnify.lms.domain.helper.ValidateHelper;
import com.learnify.lms.domain.model.Role;
import com.learnify.lms.domain.model.User;
import com.learnify.lms.domain.repository.RoleRepository;
import com.learnify.lms.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
public class AuthServiceImpl implements AuthService {
  private final UserRepository usersRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

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
        roleRepository
            .findByRoleName(ROLE_STUDENT)
            .orElseThrow(() -> new AuthException(ErrorCode.ROLE_NOT_FOUND));
    Set<Role> roles = new HashSet<>();
    roles.add(studentRole);
    User user =
        User.builder()
            .email(request.getEmail())
            .username(username)
            .password(passwordEncoder.encode(request.getPassword()))
            .fullName(request.getFullName())
            .phone(request.getPhone())
            .roles(roles)
            .build();

    User saved = usersRepository.save(user);

    return RegistrationResponse.builder()
        .email(saved.getEmail())
        .fullName(saved.getFullName())
        .username(saved.getUsername())
        .phone(saved.getPhone())
        .roles(List.of(ROLE_STUDENT))
        .createdAt(saved.getCreatedAt())
        .build();
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

    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    ResponseCookie accessTokenCookie =
        jwtService.createCookie(
            "access_token", accessToken, jwtService.getAccessTokenExpirationMs());
    ResponseCookie refreshTokenCookie =
        jwtService.createCookie(
            "refresh_token", refreshToken, jwtService.getRefreshTokenExpirationMs());
    response.addHeader("Set-Cookie", accessTokenCookie.toString());
    response.addHeader("Set-Cookie", refreshTokenCookie.toString());

    return AuthenticationResponse.builder().id(user.getId()).role(extractPrimaryRole(user)).build();
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
      if (!jwtService.isRefreshToken(refreshToken)) {
        throw new AuthException(ErrorCode.INVALID_TOKEN);
      }
      String userId = jwtService.extractSubject(refreshToken);

      User user =
          usersRepository
              .findById(UUID.fromString(userId))
              .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

      String newAccessToken = jwtService.generateAccessToken(user);
      String newRefreshToken = jwtService.generateRefreshToken(user);

      ResponseCookie accessTokenCookie =
          jwtService.createCookie(
              "access_token", newAccessToken, jwtService.getAccessTokenExpirationMs());
      ResponseCookie refreshTokenCookie =
          jwtService.createCookie(
              "refresh_token", newRefreshToken, jwtService.getRefreshTokenExpirationMs());
      response.addHeader("Set-Cookie", accessTokenCookie.toString());
      response.addHeader("Set-Cookie", refreshTokenCookie.toString());

      return AuthenticationResponse.builder()
          .id(user.getId())
          .role(extractPrimaryRole(user))
          .build();
    } catch (RuntimeException e) {
      throw new AuthException(ErrorCode.INVALID_TOKEN);
    }
  }

  private String extractPrimaryRole(User user) {
    return user.getRoles().stream().map(Role::getRoleName).sorted().findFirst().orElse(null);
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
