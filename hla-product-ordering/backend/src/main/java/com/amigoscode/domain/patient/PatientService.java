package com.amigoscode.domain.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.util.List;

@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final Clock clock;

    public Patient findById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(PatientNotFoundException::new);
    }

    public PagePatient findAll(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient save(final Patient patient) {
        return patientRepository.save(patient);
    }
}
