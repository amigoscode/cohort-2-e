package com.amigoscode.api.schedule;

import com.amigoscode.domain.schedule.Status;

public record ScheduleDto(
        Integer id,
        Integer patientId,
        Status status
) {
}
