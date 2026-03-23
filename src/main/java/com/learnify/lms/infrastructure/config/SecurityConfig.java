package com.learnify.lms.infrastructure.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnify.lms.infrastructure.security.JwtAuthenticationFilter;
import com.learnify.lms.common.base.BaseResponse;
import com.learnify.lms.common.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
  private final ObjectMapper objectMapper;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private static final String[] PUBLIC_ENDPOINTS = {
    "/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/otp/**",
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .cors(withDefaults())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth -> auth.requestMatchers(PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated())
        .exceptionHandling(
            ex ->
                ex.authenticationEntryPoint(
                        (request, response, authException) -> {
                          response.setContentType("application/json;charset=UTF-8");
                          response.setStatus(401);
                          final BaseResponse<?> errorResponse =
                              BaseResponse.builder()
                                  .success(false)
                                  .status(ErrorCode.UNAUTHORIZED.getStatusCode().value())
                                  .error(
                                      BaseResponse.Error.builder()
                                          .errorCode(ErrorCode.UNAUTHORIZED.getErrorCode())
                                          .message(ErrorCode.UNAUTHORIZED.getMessage())
                                          .build())
                                  .timestamp(LocalDateTime.now())
                                  .build();
                          response
                              .getWriter()
                              .write(objectMapper.writeValueAsString(errorResponse));
                        })
                    .accessDeniedHandler(
                        (request, response, accessDeniedException) -> {
                          response.setContentType("application/json;charset=UTF-8");
                          response.setStatus(403);
                          final BaseResponse<?> errorResponse =
                              BaseResponse.builder()
                                  .success(false)
                                  .status(ErrorCode.FORBIDDEN.getStatusCode().value())
                                  .error(
                                      BaseResponse.Error.builder()
                                          .errorCode(ErrorCode.FORBIDDEN.getErrorCode())
                                          .message(ErrorCode.FORBIDDEN.getMessage())
                                          .build())
                                  .timestamp(LocalDateTime.now())
                                  .build();
                          response
                              .getWriter()
                              .write(objectMapper.writeValueAsString(errorResponse));
                        }))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
    configuration.setExposedHeaders(List.of("Authorization"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}

