package com.learnify.lms.infrastructure.security;

import com.learnify.lms.application.service.jwt.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final IJwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = null; // kiểm tra túi quần cookie có thẻ ra vào ko (token)
    final String accessTokenCookieName = "access_token";
    final String TYPE = "type";
    final String ACCESS_TYPE = "access";
    if (request.getCookies() != null) {
      for (var cookie : request.getCookies()) {
        if (accessTokenCookieName.equals(cookie.getName())) {
          token = cookie.getValue();
          break;
        }
      }
    }

    if (token == null) { // nếu ko có không token bỏ qua và tiếp tục chuỗi filter (white-list)
      filterChain.doFilter(request, response);
      return;
    }
    try {
      var claims = jwtService.extractAllClaims(token);
      String type = claims.get(TYPE, String.class);
      if (!ACCESS_TYPE.equals(type)) {
        filterChain.doFilter(request, response);
        return;
      }
      String userId = claims.getSubject();
      if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}

