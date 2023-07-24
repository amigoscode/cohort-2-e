package com.amigoscode.api.schedule;

import com.amigoscode.domain.schedule.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleDtoMapper {

    ScheduleDto toDto(Schedule domain);

    Schedule toDomain(ScheduleDto dto);

}
