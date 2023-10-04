package com.amigoscode.external.storage.patient;

import com.amigoscode.api.patient.Gender;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "patient_mrn_unique",
                        columnNames = "mrn"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class PatientEntity {
    @Id
    @SequenceGenerator(
            name = "patient_id_seq",
            sequenceName = "patient_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String mrn;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender")
    private Gender gender;
    @Column(
            nullable = false
    )
    private ZonedDateTime dob;
    @Column(
            nullable = false
    )
    private ZonedDateTime createdAt;

}
