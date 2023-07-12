package com.amigoscode.external.storage.patient;

import com.amigoscode.domain.patient.PagePatient;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientRepository;
import com.amigoscode.domain.provider.PageProvider;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderAlreadyExistsException;
import com.amigoscode.domain.provider.ProviderRepository;
import com.amigoscode.external.storage.provider.JpaProviderRepository;
import com.amigoscode.external.storage.provider.ProviderEntity;
import com.amigoscode.external.storage.provider.ProviderEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public
class ListPatientStorageAdapter implements PatientRepository {

    private final PatientEntityMapper mapper;
    //private final List<Patient> patients = new ArrayList<>();
    private final Map<Integer, PatientEntity> patients = new HashMap<>();

    @Override
    public Optional<Patient> findById(final Integer id) {
        if(!patients.containsKey(id)) {
            return Optional.empty();
        }

        PatientEntity patientEntity = patients.entrySet().stream()
                .filter(p -> p.getKey().equals(id))
                .findFirst().get().getValue();

        return Optional.of(mapper.toDomain(patientEntity));

    }

    @Override
    public List<Patient> findAll() {
        //patients
        //Patient.builder().id(3).mrn("456").dob(ZonedDateTime.now()).createdAt(ZonedDateTime.now()).build();

        return patients.entrySet().stream().map(m -> m.getValue()).map(p -> mapper.toDomain(p)).toList();
    }

    @Override
    public Patient save(Patient patient) {
        Integer id = patients.size() + 1;
        patient.setId(id);
        patients.put(id, mapper.toEntity(patient));
        return patient;
    }
}
