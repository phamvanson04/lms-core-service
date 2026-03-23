package com.learnify.lms.presentation.dto.response.auth;

import java.time.LocalDateTime;
import java.util.List;
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
  private List<String> roles;
  private LocalDateTime createdAt;
}

