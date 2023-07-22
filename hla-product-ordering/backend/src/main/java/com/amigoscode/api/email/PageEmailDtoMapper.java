package com.amigoscode.api.email;

import com.amigoscode.api.email.EmailDto;
import com.amigoscode.api.email.PageEmailDto;
import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.email.PageEmail;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageEmailDtoMapper {

    @Mapping(target = "emails", qualifiedByName = "toEmailDtoList")
    PageEmailDto toPageDto(PageEmail domain);

    @Named("toEmailDtoList")
    @IterableMapping(qualifiedByName = "emailToEmailDto")
    List<EmailDto> toListDto(List<Email> emails);

    @Named("emailToEmailDto")
    EmailDto toDto(Email domain);
}