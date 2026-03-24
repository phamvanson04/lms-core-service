package com.learnify.lms.application.dto.request.auth;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
  private String currentPassword;
  private String newPassword;
}
