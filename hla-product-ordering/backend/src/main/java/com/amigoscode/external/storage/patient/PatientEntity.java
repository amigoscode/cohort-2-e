package com.amigoscode.external.storage.patient;

import com.amigoscode.api.patient.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PatientEntity {

    private Integer id;
    private String fullName;
    private String mrn;
    private Gender gender;
    private ZonedDateTime dob;
    private ZonedDateTime createdAt;

}
