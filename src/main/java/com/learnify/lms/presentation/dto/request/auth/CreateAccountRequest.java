package com.learnify.lms.presentation.dto.request.auth;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest implements Serializable {
  private String fullName;
  private String email;
  private String password;
  private String phone;
}

