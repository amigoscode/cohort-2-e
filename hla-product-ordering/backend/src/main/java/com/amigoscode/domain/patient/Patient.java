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
    private String mrn;
    private ZonedDateTime dob;
    private ZonedDateTime createdAt;

}
