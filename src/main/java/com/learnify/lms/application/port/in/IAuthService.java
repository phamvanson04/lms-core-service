package com.learnify.lms.application.port.in;

import com.learnify.lms.application.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.application.dto.request.auth.LoginRequest;
import com.learnify.lms.application.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.application.dto.response.auth.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
  RegistrationResponse register(CreateAccountRequest request);

  AuthenticationResponse login(LoginRequest request, HttpServletResponse response);

  AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
}
