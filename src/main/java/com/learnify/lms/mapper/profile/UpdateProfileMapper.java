package com.learnify.lms.mapper.profile;

import com.learnify.lms.dto.request.profile.UpdateProfileRequest;
import com.learnify.lms.mapper.ObjectMapper;
import com.learnify.lms.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UpdateProfileMapper extends ObjectMapper<UpdateProfileRequest, User> {
  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "fullName", ignore = true)
  @Mapping(target = "phone", ignore = true)
  void partialUpdate(@MappingTarget User entity, UpdateProfileRequest dto);
}
