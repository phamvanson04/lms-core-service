package com.learnify.lms.mapper.auth;

import com.learnify.lms.dto.response.auth.RegistrationResponse;
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
public class RegistrationMapperImpl implements RegistrationMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User toEntity(RegistrationResponse dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.fullName( dto.getFullName() );
        user.phone( dto.getPhone() );
        user.username( dto.getUsername() );

        return user.build();
    }

    @Override
    public List<User> toEntity(List<RegistrationResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( RegistrationResponse registrationResponse : dtoList ) {
            list.add( toEntity( registrationResponse ) );
        }

        return list;
    }

    @Override
    public List<RegistrationResponse> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RegistrationResponse> list = new ArrayList<RegistrationResponse>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, RegistrationResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
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
        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
    }

    @Override
    public RegistrationResponse toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        RegistrationResponse.RegistrationResponseBuilder registrationResponse = RegistrationResponse.builder();

        registrationResponse.role( roleMapper.primaryRole( entity.getRoles() ) );
        registrationResponse.createdAt( entity.getCreatedAt() );
        registrationResponse.email( entity.getEmail() );
        registrationResponse.fullName( entity.getFullName() );
        registrationResponse.phone( entity.getPhone() );
        registrationResponse.username( entity.getUsername() );

        return registrationResponse.build();
    }
}
