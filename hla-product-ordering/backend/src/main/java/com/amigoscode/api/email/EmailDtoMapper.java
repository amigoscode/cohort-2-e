package com.amigoscode.api.email;

import com.amigoscode.api.email.EmailDto;
import com.amigoscode.domain.email.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailDtoMapper {

    EmailDto toDto(Email domain);

    Email toDomain(EmailDto dto);
}
