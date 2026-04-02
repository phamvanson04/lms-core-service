package com.learnify.lms.controller.rest;

import com.learnify.lms.dto.request.auth.ChangePasswordRequest;
import com.learnify.lms.dto.request.profile.UpdateProfileRequest;
import com.learnify.lms.dto.response.BaseResponse;
import com.learnify.lms.dto.response.auth.UserProfileResponse;
import com.learnify.lms.service.interfaces.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ProfileRestController {
  private final IProfileService IProfileService;

  @GetMapping
  public BaseResponse<UserProfileResponse> getProfile() {
    UserProfileResponse profile = IProfileService.getProfile();
    return BaseResponse.success(profile, "Profile retrieved successfully");
  }

  @PutMapping
  public BaseResponse<UserProfileResponse> updateProfile(
      @RequestBody UpdateProfileRequest request) {
    UserProfileResponse profile = IProfileService.updateProfile(request);
    return BaseResponse.success(profile, "Profile updated successfully");
  }

  @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BaseResponse<UserProfileResponse> updateAvatar(@RequestPart("file") MultipartFile file) {
    UserProfileResponse profile = IProfileService.updateAvtar(file);
    return BaseResponse.success(profile, "Avatar updated successfully");
  }

  @PostMapping(
      value = "/avatar/remove-bg",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<byte[]> removeAvatarBackground(@RequestPart("file") MultipartFile file) {
    byte[] output = IProfileService.removeAvatarBackground(file);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(output);
  }

  @PutMapping("/change-password")
  public BaseResponse<Void> changePassword(@RequestBody ChangePasswordRequest request) {
    IProfileService.changePassword(request);
    return BaseResponse.success(null, "Password changed successfully");
  }

  @DeleteMapping
  public BaseResponse<Void> deleteAccount() {
    IProfileService.deleteAccount();
    return BaseResponse.success(null, "Account deleted successfully");
  }
}
