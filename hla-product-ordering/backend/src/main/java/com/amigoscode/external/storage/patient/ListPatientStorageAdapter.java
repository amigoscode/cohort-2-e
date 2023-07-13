package com.amigoscode.external.storage.patient;

import com.amigoscode.domain.patient.PagePatient;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
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
    public PagePatient findAll(final Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Patient> list;

        if (patients.values().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, patients.values().size());

            list = new ArrayList<>(patients.values()).subList(startItem, toIndex)
                    .stream()
                    .map(mapper::toDomain).toList();
        }

        return new PagePatient(
                list,
                pageable.getPageNumber() + 1,
                (int) ((double) patients.size() / pageable.getPageSize() + 0.99),
                (long) patients.size()
        );
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
