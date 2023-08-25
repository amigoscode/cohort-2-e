package com.amigoscode.domain.schedule;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.version.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class Schedule {
    private Integer id;
    private Integer patientId;
    private Status status;
    private Version version;
    private Note note;
    private Patient patient;

    public Schedule(Integer id, Integer patientId, Status status) {
        this.id = id;
        this.patientId = patientId;
        this.status =  status;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(patientId, schedule.patientId) && status == schedule.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, status);
    }
}
