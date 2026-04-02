package com.learnify.lms.mapper.auth;

import com.learnify.lms.dto.response.auth.AuthenticationResponse;
import com.learnify.lms.mapper.ObjectMapper;
import com.learnify.lms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = RoleMapper.class)
public interface AuthenticationMapper extends ObjectMapper<AuthenticationResponse, User> {
  @Override
  @Mapping(target = "role", source = "roles", qualifiedByName = "primaryRole")
  @Mapping(target = "accessToken", ignore = true)
  AuthenticationResponse toDto(User entity);
}
