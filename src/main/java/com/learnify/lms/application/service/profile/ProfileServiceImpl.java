package com.learnify.lms.application.service.profile;

import com.learnify.lms.presentation.dto.request.auth.ChangePasswordRequest;
import com.learnify.lms.presentation.dto.request.profile.UpdateProfileRequest;
import com.learnify.lms.presentation.dto.response.auth.UserProfileResponse;
import com.learnify.lms.domain.model.User;
import com.learnify.lms.infrastructure.persistence.repository.JpaUsersRepository;
import com.learnify.lms.application.service.storage.FileService;
import com.learnify.lms.common.exception.AppException;
import com.learnify.lms.common.exception.AuthException;
import com.learnify.lms.common.exception.ErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements IProfileService {
  private final JpaUsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileService fileService;

  private User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new AuthException(ErrorCode.UNAUTHENTICATED);
    }
    String userId = authentication.getName();
    return usersRepository
        .findById(UUID.fromString(userId))
        .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
  }

  @Override
  @Transactional(readOnly = true)
  public UserProfileResponse getProfile() {
    User user = getCurrentUser();

    return UserProfileResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .fullName(user.getFullName())
        .phone(user.getPhone())
        .address(user.getAddress())
        .avatarUrl(user.getAvatarUrl())
        .bio(user.getBio())
        .createdAt(user.getCreatedAt())
        .build();
  }

  @Override
  @Transactional
  public UserProfileResponse updateProfile(UpdateProfileRequest request) {
    User user = getCurrentUser();

    if (StringUtils.hasText(request.getFullName())) {
      user.setFullName(request.getFullName());
    }

    if (StringUtils.hasText(request.getPhone())) {
      if (usersRepository.existsByPhoneAndIdNot(request.getPhone(), user.getId())) {
        throw new AppException(ErrorCode.PHONE_EXISTED);
      }
      user.setPhone(request.getPhone());
    }

    if (request.getBio() != null) {
      user.setBio(request.getBio());
    }

    if (request.getAddress() != null) {
      user.setAddress(request.getAddress());
    }

    User updatedUser = usersRepository.save(user);
    return UserProfileResponse.builder()
        .id(updatedUser.getId())
        .email(updatedUser.getEmail())
        .fullName(updatedUser.getFullName())
        .phone(updatedUser.getPhone())
        .address(updatedUser.getAddress())
        .avatarUrl(updatedUser.getAvatarUrl())
        .bio(updatedUser.getBio())
        .createdAt(updatedUser.getCreatedAt())
        .build();
  }

  @Override
  public UserProfileResponse updateAvtar(MultipartFile file) {
    User user = getCurrentUser();

    if (StringUtils.hasText(user.getAvatarUrl())) {
      try {
        fileService.deleteFile(user.getAvatarUrl());
      } catch (Exception e) {
      }
    }

    String avatarUrl = fileService.uploadAvatar(file);
    user.setAvatarUrl(avatarUrl);
    User updatedUser = usersRepository.save(user);

    return UserProfileResponse.builder()
        .id(updatedUser.getId())
        .email(updatedUser.getEmail())
        .fullName(updatedUser.getFullName())
        .phone(updatedUser.getPhone())
        .address(updatedUser.getAddress())
        .avatarUrl(updatedUser.getAvatarUrl())
        .bio(updatedUser.getBio())
        .createdAt(updatedUser.getCreatedAt())
        .build();
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordRequest request) {
    User user = getCurrentUser();

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new AuthException(ErrorCode.INVALID_CREDENTIALS);
    }

    if (request.getCurrentPassword().equals(request.getNewPassword())) {
      throw new AppException(ErrorCode.SAME_PASSWORD);
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    usersRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteAccount() {
    User user = getCurrentUser();
    user.softDelete();
    usersRepository.save(user);
  }
}

