package com.learnify.lms.application.service.otp;

import com.learnify.lms.presentation.dto.response.auth.OtpMeta;

public interface OtpService {
  OtpMeta generateOtp(String email);

  boolean verifyOtp(String email, String otp);

  void invalidateOtp(String email);
}

