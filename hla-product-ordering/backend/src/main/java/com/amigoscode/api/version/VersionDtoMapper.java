package com.amigoscode.api.version;

import com.amigoscode.domain.version.Version;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VersionDtoMapper {

    VersionDto toDto(Version domain);

}
