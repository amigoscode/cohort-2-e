package com.amigoscode.api.user;

import com.amigoscode.domain.user.PageUser;
import com.amigoscode.domain.user.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageUserDtoMapper {

    @Mapping(target = "users", qualifiedByName = "toUserDtoList")
    PageUserDto toPageDto(PageUser domain);

    @Named("toUserDtoList")
    @IterableMapping(qualifiedByName = "userToUserDto")
    List<UserDto> toListDto(List<User> users);

    @Named("userToUserDto")
    @Mapping(target="password", constant = "######")
    UserDto toDto(User domain);
}