package com.amigoscode.external.storage.user;

import com.amigoscode.domain.user.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(User domain);

    User toDomain(UserEntity entity);

}
