package com.amigoscode.api.schedule;

import java.time.ZonedDateTime;

public record NoteDto(
        Integer id,
        Integer scheduleId,
        Integer scheduleVersion,
        String note,
        ZonedDateTime createdAt,
        Integer createdBy
) {
}
