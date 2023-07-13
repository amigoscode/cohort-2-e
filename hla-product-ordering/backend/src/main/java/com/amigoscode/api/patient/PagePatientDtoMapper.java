package com.amigoscode.api.patient;

import com.amigoscode.domain.patient.PagePatient;
import com.amigoscode.domain.patient.Patient;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagePatientDtoMapper {

    @Mapping(target = "patients", qualifiedByName = "toPatientDtoList")
    PagePatientDto toPageDto(PagePatient domain);

    @Named("toPatientDtoList")
    @IterableMapping(qualifiedByName = "patientToPatientDto")
    List<PatientDto> toListDto(List<Patient> patients);

    @Named("patientToPatientDto")
    PatientDto toDto(Patient domain);
}