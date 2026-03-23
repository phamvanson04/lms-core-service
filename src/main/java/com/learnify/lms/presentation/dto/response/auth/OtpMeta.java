package com.learnify.lms.presentation.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OtpMeta {
  private Integer otpTtlSeconds;
  private Integer remainingAttempts;
}

