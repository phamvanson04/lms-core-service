package com.learnify.lms.dto.response.auth;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {
  private UUID id;
  private String email;
  private String fullName;
  private String phone;
  private String address;
  private String avatarUrl;
  private String bio;
  private String role;
  private LocalDateTime createdAt;
}
