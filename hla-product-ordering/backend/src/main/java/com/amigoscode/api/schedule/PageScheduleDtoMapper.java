package com.amigoscode.api.schedule;

import com.amigoscode.domain.schedule.PageSchedule;
import com.amigoscode.domain.schedule.Schedule;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageScheduleDtoMapper {

    @Mapping(target = "schedules", qualifiedByName = "toScheduleDtoList")
    PageScheduleDto toPageDto(PageSchedule domain);

    @Named("toScheduleDtoList")
    @IterableMapping(qualifiedByName = "scheduleToScheduleDto")
    List<ScheduleDto> toListDto(List<Schedule>schedules);

    @Named("scheduleToScheduleDto")
    ScheduleDto toDto(Schedule domain);
}
