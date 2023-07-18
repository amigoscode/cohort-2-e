package com.amigoscode.external.storage.note;

import com.amigoscode.domain.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaNoteRepository extends JpaRepository<NoteEntity,Integer> {
    Optional<NoteEntity> findByScheduleIdAndScheduleVersion(Integer scheduleId, Integer scheduleVersion);
    List<NoteEntity> findByScheduleId(Integer scheduleId);

}
