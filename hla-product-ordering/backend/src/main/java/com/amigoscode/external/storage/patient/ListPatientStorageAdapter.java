package com.amigoscode.external.storage.patient;

import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Log
public
class ListPatientStorageAdapter implements PatientRepository {

    private final PatientEntityMapper mapper;
    private final Map<Integer, PatientEntity> patients = new HashMap<>();
    private static Integer id = 0;

    @Override
    public Optional<Patient> findById(final Integer id) {
        return Optional.ofNullable(patients.get(id)).map(mapper::toDomain);
    }

    @Override
    public List<Patient> findAll() {
        return patients.values().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Patient save(Patient patient) {
        id++;
        patient.setId(id);
        patients.put(id, mapper.toEntity(patient));
        return patient;
    }

    @Override
    public void deleteAll() {
        patients.clear();
    }
}
