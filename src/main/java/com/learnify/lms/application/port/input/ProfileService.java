package com.learnify.lms.application.port.input;

import com.learnify.lms.application.dto.request.auth.ChangePasswordRequest;
import com.learnify.lms.application.dto.request.profile.UpdateProfileRequest;
import com.learnify.lms.application.dto.response.auth.UserProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
  UserProfileResponse getProfile();

  UserProfileResponse updateProfile(UpdateProfileRequest request);

  UserProfileResponse updateAvtar(MultipartFile file);

  void changePassword(ChangePasswordRequest request);

  void deleteAccount();
}
