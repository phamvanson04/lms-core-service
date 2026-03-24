package com.learnify.lms.application.dto.response.auth;

import java.time.LocalDateTime;
import java.util.List;
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
  private List<String> roles;
  private LocalDateTime createdAt;
}
