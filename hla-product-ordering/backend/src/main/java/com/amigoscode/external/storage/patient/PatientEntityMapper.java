package com.amigoscode.external.storage.patient;

import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.external.storage.provider.ProviderEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PatientEntityMapper {

    PatientEntity toEntity(Patient domain);

    Patient toDomain(PatientEntity entity);

}
