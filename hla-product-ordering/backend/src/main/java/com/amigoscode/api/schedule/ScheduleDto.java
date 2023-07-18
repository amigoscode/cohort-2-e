package com.amigoscode.api.schedule;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.schedule.Status;
import com.amigoscode.domain.version.Version;

public record ScheduleDto(
        Integer id,
        Integer patientId,
        Status status,
        Version version,
        Note note
) {
}
