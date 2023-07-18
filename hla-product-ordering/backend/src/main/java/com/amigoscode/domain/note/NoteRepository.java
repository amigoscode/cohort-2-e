package com.amigoscode.domain.note;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NoteRepository {
    Optional<Note> findByScheduleIdAndVersion(Integer scheduleId, Integer version);
    Optional<Note> findById(Integer id);

    PageNote findAll(Pageable pageable);

    Note save(Note note);

    void update(Note note);

    void removeById(Integer id);
    void removeByScheduleId(Integer scheduleId);


}
