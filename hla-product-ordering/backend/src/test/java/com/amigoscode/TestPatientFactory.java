package com.amigoscode;


import com.amigoscode.domain.patient.Gender;
import com.amigoscode.domain.patient.Patient;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestPatientFactory {

    private static int patientSequence = 0;

    public static Patient create() {
        patientSequence++;

        return new Patient(
                null,
                "John Doe",
                "mrn" + patientSequence,
                Gender.MALE,
                ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")).plusDays(patientSequence)
        );
    }

}
