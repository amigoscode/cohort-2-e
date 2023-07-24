package com.amigoscode.external.storage.version;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VersionEntity {

    @Id
    @SequenceGenerator(
            name = "version_id_seq",
            sequenceName = "version_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "version_id_seq"
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
  /*  @Column(name = "state", columnDefinition = "state_type")*/
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
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
