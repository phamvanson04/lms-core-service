package com.learnify.lms.mapper.auth;

import com.learnify.lms.dto.response.auth.AuthenticationResponse;
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
public class AuthenticationMapperImpl implements AuthenticationMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User toEntity(AuthenticationResponse dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        return user.build();
    }

    @Override
    public List<User> toEntity(List<AuthenticationResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( AuthenticationResponse authenticationResponse : dtoList ) {
            list.add( toEntity( authenticationResponse ) );
        }

        return list;
    }

    @Override
    public List<AuthenticationResponse> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AuthenticationResponse> list = new ArrayList<AuthenticationResponse>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, AuthenticationResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
    }

    @Override
    public AuthenticationResponse toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        AuthenticationResponse.AuthenticationResponseBuilder authenticationResponse = AuthenticationResponse.builder();

        authenticationResponse.role( roleMapper.primaryRole( entity.getRoles() ) );
        authenticationResponse.id( entity.getId() );

        return authenticationResponse.build();
    }
}
