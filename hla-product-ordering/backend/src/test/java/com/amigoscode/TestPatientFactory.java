package com.amigoscode;


import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserRole;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestPatientFactory {

    private static int patientSequence = 0;

    public static Patient create() {
        patientSequence++;

        return new Patient(
                null,
                "mrn" + patientSequence,
                ZonedDateTime.of(2003, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")).plusDays(patientSequence)
        );
    }

}
