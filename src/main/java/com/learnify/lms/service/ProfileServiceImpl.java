package com.learnify.lms.service;

import com.learnify.lms.dto.request.auth.ChangePasswordRequest;
import com.learnify.lms.dto.request.profile.UpdateProfileRequest;
import com.learnify.lms.dto.response.auth.UserProfileResponse;
import com.learnify.lms.mapper.profile.UpdateProfileMapper;
import com.learnify.lms.mapper.profile.UserProfileMapper;
import com.learnify.lms.service.interfaces.IProfileService;
import com.learnify.lms.exception.AppException;
import com.learnify.lms.exception.AuthException;
import com.learnify.lms.exception.ErrorCode;
import com.learnify.lms.model.Avatar;
import com.learnify.lms.model.User;
import com.learnify.lms.repository.JpaUserRepository;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
  private static final int MAX_AVATARS_PER_USER = 3;

  private final JpaUserRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileUploadService fileUploadService;
  private final AiBackgroundRemovalService aiBackgroundRemovalService;
  private final UpdateProfileMapper updateProfileMapper;
  private final UserProfileMapper userProfileMapper;

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
    return userProfileMapper.toDto(user);
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

    updateProfileMapper.partialUpdate(user, request);
    User updatedUser = usersRepository.save(user);
    return UserProfileResponse.builder()
      .id(updatedUser.getId())
      .email(updatedUser.getEmail())
      .fullName(updatedUser.getFullName())
      .phone(updatedUser.getPhone())
      .address(updatedUser.getAddress())
      .avatarUrl(updatedUser.getAvatarUrl())
      .bio(updatedUser.getBio())
      .role(userProfileMapper.toDto(updatedUser).getRole())
      .createdAt(updatedUser.getCreatedAt())
      .build();
  }

  @Override
  @Transactional
  public UserProfileResponse updateAvtar(MultipartFile file) {
    User user = getCurrentUser();

    List<Avatar> activeAvatars =
        user.getAvatars() == null
            ? List.of()
            : user.getAvatars().stream()
                .filter(avatar -> avatar.getDeletedAt() == null)
                .sorted(
                    Comparator.comparing(
                        Avatar::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

    if (activeAvatars.size() >= MAX_AVATARS_PER_USER) {
      Avatar oldestAvatar = activeAvatars.get(0);
      fileUploadService.deleteFile(oldestAvatar.getUrl());
      if (user.getAvatars() != null) {
        user.getAvatars().remove(oldestAvatar);
      }
    }

    String avatarUrl = fileUploadService.uploadAvatar(file, user.getId().toString());

    Avatar avatar = Avatar.builder().url(avatarUrl).user(user).build();
    if (user.getAvatars() == null) {
      user.setAvatars(new HashSet<>());
    }
    user.getAvatars().add(avatar);

    User updatedUser = usersRepository.save(user);
    return UserProfileResponse.builder()
      .id(updatedUser.getId())
      .email(updatedUser.getEmail())
      .fullName(updatedUser.getFullName())
      .phone(updatedUser.getPhone())
      .address(updatedUser.getAddress())
      .avatarUrl(updatedUser.getAvatarUrl())
      .bio(updatedUser.getBio())
      .role(userProfileMapper.toDto(updatedUser).getRole())
      .createdAt(updatedUser.getCreatedAt())
      .build();
  }

  @Override
  @Transactional(readOnly = true)
  public byte[] removeAvatarBackground(MultipartFile file) {
    getCurrentUser();
    return aiBackgroundRemovalService.removeBackground(file);
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
