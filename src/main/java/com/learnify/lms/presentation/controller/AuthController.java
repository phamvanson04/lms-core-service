package com.learnify.lms.presentation.controller;

import static com.learnify.lms.common.constants.Constants.LOGIN_SUCCESSFUL;
import static com.learnify.lms.common.constants.Constants.REGISTERED_SUCCESSFULLY;

import com.learnify.lms.presentation.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.presentation.dto.request.auth.LoginRequest;
import com.learnify.lms.presentation.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.presentation.dto.response.auth.RegistrationResponse;
import com.learnify.lms.application.service.auth.IAuthService;
import com.learnify.lms.common.base.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final IAuthService authService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<RegistrationResponse> register(@RequestBody CreateAccountRequest request) {
    RegistrationResponse response = authService.register(request);
    return BaseResponse.create(response, REGISTERED_SUCCESSFULLY);
  }

  @PostMapping("/login")
  public BaseResponse<AuthenticationResponse> login(
      @RequestBody LoginRequest request, HttpServletResponse response) {
    AuthenticationResponse authResponse = authService.login(request, response);
    return BaseResponse.success(authResponse, LOGIN_SUCCESSFUL);
  }

  @PostMapping("/refresh")
  public BaseResponse<AuthenticationResponse> refresh(
      HttpServletRequest request, HttpServletResponse response) {
    AuthenticationResponse authResponse = authService.refreshToken(request, response);
    return BaseResponse.success(authResponse, "Token refreshed successfully");
  }
}

