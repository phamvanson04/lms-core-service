package com.learnify.lms.service.interfaces;

import com.learnify.lms.dto.request.auth.ChangePasswordRequest;
import com.learnify.lms.dto.request.profile.UpdateProfileRequest;
import com.learnify.lms.dto.response.auth.UserProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IProfileService {
  UserProfileResponse getProfile();

  UserProfileResponse updateProfile(UpdateProfileRequest request);

  UserProfileResponse updateAvtar(MultipartFile file);

  byte[] removeAvatarBackground(MultipartFile file);

  void changePassword(ChangePasswordRequest request);

  void deleteAccount();
}
