package com.amigoscode.external.storage.note;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
