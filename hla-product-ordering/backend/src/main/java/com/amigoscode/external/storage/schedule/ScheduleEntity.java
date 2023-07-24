package com.amigoscode.external.storage.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
    private Integer patientId;
    @Enumerated(EnumType.STRING)
    //@Column(name = "status", columnDefinition = "status_type")
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEntity that = (ScheduleEntity) o;
        return id.equals(that.id) && patientId.equals(that.patientId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, status);
    }
}
