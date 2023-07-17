package com.amigoscode.external.storage.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ScheduleEntity {

    @Id
    @SequenceGenerator(
            name = "schedule_id_seq",
            sequenceName = "schedule_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "schedule_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private Integer patient_id;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEntity that = (ScheduleEntity) o;
        return id.equals(that.id) && patient_id.equals(that.patient_id) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient_id, status);
    }
}