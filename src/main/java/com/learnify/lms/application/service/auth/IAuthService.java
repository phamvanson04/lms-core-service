package com.learnify.lms.application.service.auth;

import com.learnify.lms.presentation.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.presentation.dto.request.auth.LoginRequest;
import com.learnify.lms.presentation.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.presentation.dto.response.auth.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
  RegistrationResponse register(CreateAccountRequest request);

  AuthenticationResponse login(LoginRequest request, HttpServletResponse response);

  AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
}

