package com.amigoscode.domain.patient;

import com.amigoscode.domain.provider.PageProvider;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final Clock clock;

    public Patient findById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(PatientNotFoundException::new);
    }

    /*public PagePatient findAll(Pageable pageable) {
        return new PagePatient();
    }*/
    public List<Patient> findAll() {
        return null;
    }
}
