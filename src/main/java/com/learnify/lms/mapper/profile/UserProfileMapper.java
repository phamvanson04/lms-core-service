package com.learnify.lms.mapper.profile;

import com.learnify.lms.dto.response.auth.UserProfileResponse;
import com.learnify.lms.mapper.ObjectMapper;
import com.learnify.lms.mapper.auth.RoleMapper;
import com.learnify.lms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = RoleMapper.class)
public interface UserProfileMapper extends ObjectMapper<UserProfileResponse, User> {
  @Override
  @Mapping(target = "role", source = "roles", qualifiedByName = "primaryRole")
  UserProfileResponse toDto(User entity);
}
