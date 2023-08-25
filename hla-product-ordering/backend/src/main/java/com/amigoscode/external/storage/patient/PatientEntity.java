package com.amigoscode.external.storage.patient;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PatientEntity {

    private Integer id;
    private String fullName;
    private String mrn;
    private ZonedDateTime dob;
    private ZonedDateTime createdAt;

}
