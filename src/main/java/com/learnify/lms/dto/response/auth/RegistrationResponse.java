package com.learnify.lms.dto.response.auth;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
  private String username;
  private String email;
  private String fullName;
  private String phone;
  private String role;
  private LocalDateTime createdAt;
}
