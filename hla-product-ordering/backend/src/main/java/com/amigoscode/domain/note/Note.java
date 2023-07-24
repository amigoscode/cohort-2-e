package com.amigoscode.domain.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class Note {

    private Integer id;
    private Integer scheduleId;
    private Integer scheduleVersion;
    private String note;
    private ZonedDateTime createdAt;
    private Integer createdBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(scheduleId, note.scheduleId) && Objects.equals(scheduleVersion, note.scheduleVersion) && Objects.equals(this.note, note.note) && Objects.equals(createdAt.toInstant(), note.createdAt.toInstant()) && Objects.equals(createdBy, note.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleId, scheduleVersion, note, createdAt, createdBy);
    }
}
