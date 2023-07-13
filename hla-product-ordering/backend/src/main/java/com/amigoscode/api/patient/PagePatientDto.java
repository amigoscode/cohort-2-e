package com.amigoscode.api.patient;

import java.util.List;

public record PagePatientDto(
        List<PatientDto> patients,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {
}
