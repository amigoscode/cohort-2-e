package com.amigoscode.api.version;

import com.amigoscode.domain.version.PageVersion;
import com.amigoscode.domain.version.Version;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageVersionDtoMapper {

    @Mapping(target = "versions", qualifiedByName = "toVersionDtoList")
    PageVersionDto toPageDto(PageVersion domain);

    @Named("toVersionDtoList")
    @IterableMapping(qualifiedByName = "versionToVersionDto")
    List<VersionDto> toListDto(List<Version> versions);

    @Named("versionToVersionDto")
    VersionDto toDto(Version domain);
}