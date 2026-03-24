package com.learnify.lms.application.port.output;

import com.learnify.lms.application.dto.response.auth.OtpMeta;

public interface OtpService {
  OtpMeta generateOtp(String email);

  boolean verifyOtp(String email, String otp);

  void invalidateOtp(String email);
}
