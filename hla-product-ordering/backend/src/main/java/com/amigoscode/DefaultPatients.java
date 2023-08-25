package com.amigoscode;

import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Log
public class DefaultPatients implements CommandLineRunner {

    private final PatientService patientService;

    public DefaultPatients(PatientService patientService) {
        this.patientService = patientService;
    }

    private final Patient patient1 = new Patient(
            null,
            "John Doe",
            "mrn1",
            ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.now()
    );

    private final Patient patient2 = new Patient(
            null,
            "Frank Doe",
            "mrn2",
            ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.now()
    );

    private final Patient patient3 = new Patient(
            null,
            "Henry Doe",
            "mrn3",
            ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.now()
    );
    private final Patient patient4 = new Patient(
            null,
            "Milly Doe",
            "mrn3",
            ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.now()
    );
    private final Patient patient5 = new Patient(
            null,
            "Miley Doe",
            "mrn3",
            ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.now()
    );
    private final Patient patient6 = new Patient(
            null,
            "Kale Doe",
            "mrn3",
            ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.now()
    );

    @Override
    public void run(String... args) {
        try {
            addPatient(patient1);
            addPatient(patient2);
            addPatient(patient3);
            addPatient(patient4);
            addPatient(patient5);
            addPatient(patient6);

        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void addPatient(Patient patient) {
        patientService.save(patient);
    }
}
