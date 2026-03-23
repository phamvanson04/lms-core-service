package com.learnify.lms.presentation.controller;

import com.learnify.lms.presentation.dto.request.auth.ChangePasswordRequest;
import com.learnify.lms.presentation.dto.request.profile.UpdateProfileRequest;
import com.learnify.lms.presentation.dto.response.auth.UserProfileResponse;
import com.learnify.lms.application.service.profile.IProfileService;
import com.learnify.lms.common.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ProfileController {
  private final IProfileService profileService;

  @GetMapping
  public BaseResponse<UserProfileResponse> getProfile() {
    UserProfileResponse profile = profileService.getProfile();
    return BaseResponse.success(profile, "Profile retrieved successfully");
  }

  @PutMapping
  public BaseResponse<UserProfileResponse> updateProfile(
      @RequestBody UpdateProfileRequest request) {
    UserProfileResponse profile = profileService.updateProfile(request);
    return BaseResponse.success(profile, "Profile updated successfully");
  }

  @PutMapping("/avatar")
  public BaseResponse<UserProfileResponse> updateAvatar(@RequestParam("file") MultipartFile file) {
    UserProfileResponse profile = profileService.updateAvtar(file);
    return BaseResponse.success(profile, "Avatar updated successfully");
  }

  @PutMapping("/change-password")
  public BaseResponse<Void> changePassword(@RequestBody ChangePasswordRequest request) {
    profileService.changePassword(request);
    return BaseResponse.success(null, "Password changed successfully");
  }

  @DeleteMapping
  public BaseResponse<Void> deleteAccount() {
    profileService.deleteAccount();
    return BaseResponse.success(null, "Account deleted successfully");
  }
}

