package com.learnify.lms.mapper.profile;

import com.learnify.lms.dto.request.profile.UpdateProfileRequest;
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
public class UpdateProfileMapperImpl implements UpdateProfileMapper {

    @Override
    public User toEntity(UpdateProfileRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.address( dto.getAddress() );
        user.bio( dto.getBio() );
        user.fullName( dto.getFullName() );
        user.phone( dto.getPhone() );

        return user.build();
    }

    @Override
    public UpdateProfileRequest toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();

        return updateProfileRequest;
    }

    @Override
    public List<User> toEntity(List<UpdateProfileRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UpdateProfileRequest updateProfileRequest : dtoList ) {
            list.add( toEntity( updateProfileRequest ) );
        }

        return list;
    }

    @Override
    public List<UpdateProfileRequest> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UpdateProfileRequest> list = new ArrayList<UpdateProfileRequest>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, UpdateProfileRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getBio() != null ) {
            entity.setBio( dto.getBio() );
        }
    }
}
