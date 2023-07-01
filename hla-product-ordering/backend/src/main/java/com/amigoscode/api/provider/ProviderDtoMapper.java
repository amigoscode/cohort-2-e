package com.amigoscode.api.provider;

import com.amigoscode.domain.provider.Provider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderDtoMapper {

    ProviderDto toDto(Provider domain);

    Provider toDomain(ProviderDto dto);
}