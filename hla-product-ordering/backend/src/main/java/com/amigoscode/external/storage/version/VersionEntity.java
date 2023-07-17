package com.amigoscode.external.storage.version;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VersionEntity {

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
    @Column(nullable = false)
    private Integer version;
    @Column(nullable = false)
    private Integer scheduleId;
    @Column(nullable = false)
    private Integer updatedBy;
    @Column(nullable = false)
    private ZonedDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(nullable = false)
    private ZonedDateTime startDate;
    @Column(nullable = false)
    private ZonedDateTime endDate;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Integer schedulePeriod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionEntity that = (VersionEntity) o;
        return id.equals(that.id) && version.equals(that.version) && scheduleId.equals(that.scheduleId) && updatedBy.equals(that.updatedBy) && updatedAt.equals(that.updatedAt) && state == that.state && startDate.equals(that.startDate) && endDate.equals(that.endDate) && quantity.equals(that.quantity) && schedulePeriod.equals(that.schedulePeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, scheduleId, updatedBy, updatedAt, state, startDate, endDate, quantity, schedulePeriod);
    }
}
