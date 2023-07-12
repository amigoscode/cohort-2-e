package com.amigoscode.domain.patient;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    Optional<Patient> findById(Integer id);

    //PagePatient findAll(Pageable pageable);
    List<Patient> findAll();

    Patient save(Patient patient);

}
