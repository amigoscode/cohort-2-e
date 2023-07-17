package com.amigoscode.api.version;

import com.amigoscode.domain.version.State;

import java.time.ZonedDateTime;

public record VersionDto(
        Integer id,
        Integer version,
        Integer scheduleId,
        Integer updatedBy,
        ZonedDateTime updatedAt,
        State state,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        Integer quantity,
        Integer schedulePeriod
) {
}
