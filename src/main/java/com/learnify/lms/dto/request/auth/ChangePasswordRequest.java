package com.learnify.lms.dto.request.auth;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
  private String currentPassword;
  private String newPassword;
}
