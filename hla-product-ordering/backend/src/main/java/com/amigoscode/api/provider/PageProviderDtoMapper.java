package com.amigoscode.api.provider;

import com.amigoscode.domain.provider.PageProvider;
import com.amigoscode.domain.provider.Provider;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageProviderDtoMapper {

    @Mapping(target = "providers", qualifiedByName = "toProviderDtoList")
    PageProviderDto toPageDto(PageProvider domain);

    @Named("toProviderDtoList")
    @IterableMapping(qualifiedByName = "providerToProviderDto")
    List<ProviderDto> toListDto(List<Provider> providers);

    @Named("providerToProviderDto")
    ProviderDto toDto(Provider domain);
}