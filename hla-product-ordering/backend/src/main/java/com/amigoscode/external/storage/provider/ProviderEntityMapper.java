package com.amigoscode.external.storage.provider;

import com.amigoscode.domain.provider.Provider;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProviderEntityMapper {

    ProviderEntity toEntity(Provider domain);

    Provider toDomain(ProviderEntity entity);

}
