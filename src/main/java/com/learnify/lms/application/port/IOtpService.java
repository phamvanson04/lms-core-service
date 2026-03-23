package com.learnify.lms.application.port;

import com.learnify.lms.application.dto.response.auth.OtpMeta;

public interface IOtpService {
  OtpMeta generateOtp(String email);

  boolean verifyOtp(String email, String otp);

  void invalidateOtp(String email);
}
