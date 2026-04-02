package com.learnify.lms.controller.rest;

import static com.learnify.lms.constants.Constants.LOGIN_SUCCESSFUL;
import static com.learnify.lms.constants.Constants.REGISTERED_SUCCESSFULLY;

import com.learnify.lms.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.dto.request.auth.LoginRequest;
import com.learnify.lms.dto.response.BaseResponse;
import com.learnify.lms.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.dto.response.auth.RegistrationResponse;
import com.learnify.lms.service.interfaces.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
  private final IAuthService IAuthService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<RegistrationResponse> register(@RequestBody CreateAccountRequest request) {
    RegistrationResponse response = IAuthService.register(request);
    return BaseResponse.create(response, REGISTERED_SUCCESSFULLY);
  }

  @PostMapping("/login")
  public BaseResponse<AuthenticationResponse> login(
      @RequestBody LoginRequest request, HttpServletResponse response) {
    AuthenticationResponse authResponse = IAuthService.login(request, response);
    return BaseResponse.success(authResponse, LOGIN_SUCCESSFUL);
  }

  @PostMapping("/refresh")
  public BaseResponse<AuthenticationResponse> refresh(
      HttpServletRequest request, HttpServletResponse response) {
    AuthenticationResponse authResponse = IAuthService.refreshToken(request, response);
    return BaseResponse.success(authResponse, "Token refreshed successfully");
  }
}
