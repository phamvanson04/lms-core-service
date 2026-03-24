package com.learnify.lms.infrastructure.security;

import com.learnify.lms.domain.exception.AppException;
import com.learnify.lms.domain.exception.ErrorCode;
import com.learnify.lms.domain.model.User;
import com.learnify.lms.domain.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findById(UUID.fromString(username))
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    return new UserPrincipal(user);
  }
}
