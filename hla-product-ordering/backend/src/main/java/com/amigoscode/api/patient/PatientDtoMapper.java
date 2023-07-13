package com.amigoscode.api.patient;

import com.amigoscode.domain.patient.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientDtoMapper {

    PatientDto toDto(Patient domain);

    Patient toDomain(PatientDto dto);
}