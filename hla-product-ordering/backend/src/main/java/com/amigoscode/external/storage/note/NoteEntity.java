package com.amigoscode.external.storage.note;

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
public class NoteEntity {

    @Id
    @SequenceGenerator(
            name = "note_id_seq",
            sequenceName = "note_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "note_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private Integer scheduleId;
    @Column(
            nullable = false
    )
    private Integer scheduleVersion;

    @Column(
            nullable = false
    )
    private String note;
    @Column(
            nullable = false
    )
    private ZonedDateTime createdAt;

    @Column(
            nullable = false
    )
    private Integer createdBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEntity that = (NoteEntity) o;
        return id.equals(that.id) && scheduleId.equals(that.scheduleId) && scheduleVersion.equals(that.scheduleVersion) && createdAt.equals(that.createdAt) && note.equals(that.note) && createdBy.equals(that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleId, scheduleVersion, createdAt, note, createdBy);
    }
}
