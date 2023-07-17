package com.amigoscode.api.schedule;

import java.util.List;

public record PageScheduleDto(
    List<ScheduleDto> schedules,
    Integer currentPage,
    Integer totalPages,
    Long totalElements
)
{}
