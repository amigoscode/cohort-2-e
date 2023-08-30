package com.amigoscode.api.version;

import java.time.ZonedDateTime;

public record VersionDto(
        Integer id,
        Integer version,
        Integer scheduleId,
        Integer updatedBy,
        ZonedDateTime updatedAt,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        Integer quantity,
        Integer schedulePeriod
) {
}
