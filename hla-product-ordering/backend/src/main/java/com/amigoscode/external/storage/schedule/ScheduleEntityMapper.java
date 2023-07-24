package com.amigoscode.external.storage.schedule;

import com.amigoscode.domain.schedule.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleEntityMapper {

    Schedule toDomain(ScheduleEntity entity);

    ScheduleEntity toEntity(Schedule domain);

}
