package com.learnify.lms.mapper.auth;

import com.learnify.lms.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.mapper.ObjectMapper;
import com.learnify.lms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateAccountMapper extends ObjectMapper<CreateAccountRequest, User> {
  @Override
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "username", ignore = true)
  @Mapping(target = "roles", ignore = true)
  User toEntity(CreateAccountRequest dto);
}
