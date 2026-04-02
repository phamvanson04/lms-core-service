package com.learnify.lms.service.interfaces;

import com.learnify.lms.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.dto.request.auth.LoginRequest;
import com.learnify.lms.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.dto.response.auth.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
  RegistrationResponse register(CreateAccountRequest request);

  AuthenticationResponse login(LoginRequest request, HttpServletResponse response);

  AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
}
