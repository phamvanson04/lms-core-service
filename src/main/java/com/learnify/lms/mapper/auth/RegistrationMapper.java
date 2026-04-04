package com.learnify.lms.mapper.auth;

import com.learnify.lms.dto.response.auth.RegistrationResponse;
import com.learnify.lms.mapper.ObjectMapper;
import com.learnify.lms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = RoleMapper.class)
public interface RegistrationMapper extends ObjectMapper<RegistrationResponse, User> {
  @Override
  @Mapping(target = "role", source = "roles", qualifiedByName = "primaryRole")
  RegistrationResponse toDto(User entity);
}
