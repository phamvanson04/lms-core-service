package com.learnify.lms.service.interfaces;

import com.learnify.lms.dto.response.auth.OtpMeta;

public interface IOtpService {
  OtpMeta generateOtp(String email);

  boolean verifyOtp(String email, String otp);

  void invalidateOtp(String email);
}
