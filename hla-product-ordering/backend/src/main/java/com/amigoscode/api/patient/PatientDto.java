package com.amigoscode.api.patient;

import java.time.ZonedDateTime;

public record PatientDto(
        Integer id,
        String mrn,
        ZonedDateTime dob,
        ZonedDateTime createdAt
) {}
