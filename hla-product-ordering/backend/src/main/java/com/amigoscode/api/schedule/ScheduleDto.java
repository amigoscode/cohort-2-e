package com.amigoscode.api.schedule;

import com.amigoscode.api.patient.PatientDto;
import com.amigoscode.api.version.VersionDto;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.schedule.Status;

public record ScheduleDto(
        Integer id,
        Integer patientId,
        Status status,
        VersionDto version,
        NoteDto note,
        PatientDto patient
) {
}
