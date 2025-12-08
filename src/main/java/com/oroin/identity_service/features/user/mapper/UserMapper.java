package com.oroin.identity_service.features.user.mapper;

import com.oroin.identity_service.features.user.dto.response.UserResponse;
import com.oroin.identity_service.features.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "emailAddress", source = "emailAddress")
    @Mapping(target = "rank", source = "rank")
    @Mapping(target = "position", source = "position")
    UserResponse toFullUserResponse(User user);
}
