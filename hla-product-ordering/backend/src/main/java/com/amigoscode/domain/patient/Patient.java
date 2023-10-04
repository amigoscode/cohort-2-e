package com.amigoscode.domain.patient;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
public class Patient {

    private Integer id;
    private String name;
    private String mrn;
    private Gender gender;
    private ZonedDateTime dob;
    private ZonedDateTime createdAt;

}
