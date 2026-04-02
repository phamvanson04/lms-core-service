package com.learnify.lms.security;

import com.learnify.lms.exception.AppException;
import com.learnify.lms.exception.ErrorCode;
import com.learnify.lms.model.User;
import com.learnify.lms.repository.JpaUserRepository;
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
  private final JpaUserRepository jpaUserRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        jpaUserRepository
            .findById(UUID.fromString(username))
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    return new UserPrincipal(user);
  }
}
