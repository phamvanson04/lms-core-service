package com.learnify.lms.dto.request.profile;

import lombok.Getter;

@Getter
public class UpdateProfileRequest {
  private String fullName;
  private String phone;
  private String bio;
  private String address;
}
