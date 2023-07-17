package com.amigoscode.external.storage.version;

import com.amigoscode.domain.version.Version;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VersionEntityMapper {

    Version toDomain(VersionEntity entity);

    VersionEntity toEntity(Version domain);
}
