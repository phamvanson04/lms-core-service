package com.learnify.lms.presentation.dto.request.auth;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
  private String currentPassword;
  private String newPassword;
}

