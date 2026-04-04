package com.learnify.lms.mapper.auth;

import com.learnify.lms.dto.request.auth.CreateAccountRequest;
import com.learnify.lms.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-02T10:21:43+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250611-0653, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CreateAccountMapperImpl implements CreateAccountMapper {

    @Override
    public CreateAccountRequest toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        CreateAccountRequest.CreateAccountRequestBuilder createAccountRequest = CreateAccountRequest.builder();

        createAccountRequest.email( entity.getEmail() );
        createAccountRequest.fullName( entity.getFullName() );
        createAccountRequest.password( entity.getPassword() );
        createAccountRequest.phone( entity.getPhone() );

        return createAccountRequest.build();
    }

    @Override
    public List<User> toEntity(List<CreateAccountRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( CreateAccountRequest createAccountRequest : dtoList ) {
            list.add( toEntity( createAccountRequest ) );
        }

        return list;
    }

    @Override
    public List<CreateAccountRequest> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CreateAccountRequest> list = new ArrayList<CreateAccountRequest>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, CreateAccountRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getFullName() != null ) {
            entity.setFullName( dto.getFullName() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
    }

    @Override
    public User toEntity(CreateAccountRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.fullName( dto.getFullName() );
        user.phone( dto.getPhone() );

        return user.build();
    }
}
