package com.learnify.lms.application.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OtpMeta {
  private Integer otpTtlSeconds;
  private Integer remainingAttempts;
}
