package com.learnify.lms.mapper.profile;

import com.learnify.lms.dto.response.auth.UserProfileResponse;
import com.learnify.lms.mapper.auth.RoleMapper;
import com.learnify.lms.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-02T10:21:43+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250611-0653, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User toEntity(UserProfileResponse dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.address( dto.getAddress() );
        user.bio( dto.getBio() );
        user.email( dto.getEmail() );
        user.fullName( dto.getFullName() );
        user.phone( dto.getPhone() );

        return user.build();
    }

    @Override
    public List<User> toEntity(List<UserProfileResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserProfileResponse userProfileResponse : dtoList ) {
            list.add( toEntity( userProfileResponse ) );
        }

        return list;
    }

    @Override
    public List<UserProfileResponse> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserProfileResponse> list = new ArrayList<UserProfileResponse>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, UserProfileResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getBio() != null ) {
            entity.setBio( dto.getBio() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getFullName() != null ) {
            entity.setFullName( dto.getFullName() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getAvatarUrl() != null ) {
            entity.setAvatarUrl( dto.getAvatarUrl() );
        }
    }

    @Override
    public UserProfileResponse toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserProfileResponse.UserProfileResponseBuilder userProfileResponse = UserProfileResponse.builder();

        userProfileResponse.role( roleMapper.primaryRole( entity.getRoles() ) );
        userProfileResponse.address( entity.getAddress() );
        userProfileResponse.avatarUrl( entity.getAvatarUrl() );
        userProfileResponse.bio( entity.getBio() );
        userProfileResponse.createdAt( entity.getCreatedAt() );
        userProfileResponse.email( entity.getEmail() );
        userProfileResponse.fullName( entity.getFullName() );
        userProfileResponse.id( entity.getId() );
        userProfileResponse.phone( entity.getPhone() );

        return userProfileResponse.build();
    }
}
