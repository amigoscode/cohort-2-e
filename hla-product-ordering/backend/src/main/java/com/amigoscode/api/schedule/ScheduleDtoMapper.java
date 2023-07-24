package com.amigoscode.api.schedule;

import com.amigoscode.domain.schedule.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleDtoMapper {
    @Mapping(source = "version", target = "versionDto")
    @Mapping(source = "note", target = "noteDto")
    ScheduleDto toDto(Schedule domain);

    @Mapping(source = "versionDto", target = "version")
    @Mapping(source = "noteDto", target = "note")
    Schedule toDomain(ScheduleDto dto);

}
