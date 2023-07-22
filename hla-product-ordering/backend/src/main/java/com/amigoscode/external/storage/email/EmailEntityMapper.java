package com.amigoscode.external.storage.email;

import com.amigoscode.domain.email.Email;
import com.amigoscode.external.storage.email.EmailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailEntityMapper {

    Email toDomain(EmailEntity entity);

    EmailEntity toEntity(Email domain);
}
