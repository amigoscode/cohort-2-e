package com.amigoscode.domain.version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class Version {

    private Integer id;
    private Integer version;
    private Integer scheduleId;
    private Integer updatedBy;
    private ZonedDateTime updatedAt;
    private State state;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private Integer quantity;
    private Integer schedulePeriod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version1 = (Version) o;
        return id.equals(version1.id) && version.equals(version1.version) && scheduleId.equals(version1.scheduleId) && updatedBy.equals(version1.updatedBy) && updatedAt.toInstant().equals(version1.updatedAt.toInstant()) && state == version1.state && startDate.toInstant().equals(version1.startDate.toInstant()) && endDate.toInstant().equals(version1.endDate.toInstant()) && quantity.equals(version1.quantity) && schedulePeriod.equals(version1.schedulePeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, scheduleId, updatedBy, updatedAt, state, startDate, endDate, quantity, schedulePeriod);
    }
}
