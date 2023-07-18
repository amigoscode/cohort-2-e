package com.amigoscode.external.storage.note;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.note.NoteAlreadyExistsException;
import com.amigoscode.domain.note.NoteRepository;
import com.amigoscode.domain.note.PageNote;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class NoteStorageAdapter implements NoteRepository {
    private final JpaNoteRepository noteRepository;

    private final NoteEntityMapper mapper;


    @Override
    public Optional<Note> findByScheduleIdAndVersion(Integer scheduleId, Integer version) {
        return noteRepository.findByScheduleIdAndScheduleVersion(scheduleId, version).map(mapper::toDomain);
    }

    @Override
    public Optional<Note> findById(final Integer id) {
        return noteRepository.findById(id).map(mapper::toDomain);
    }


    @Override
    public PageNote findAll(Pageable pageable) {
        Page<NoteEntity> pageOfNotesEntity = noteRepository.findAll(pageable);
        List<Note> notesOnCurrentPage = pageOfNotesEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageNote(
                notesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfNotesEntity.getTotalPages(),
                pageOfNotesEntity.getTotalElements()
        );
    }

    @Override
    public Note save(Note note) {
        try {
            NoteEntity saved = noteRepository.save(mapper.toEntity(note));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            log.warning("Note " + note.getId() + " already exits in db");
            throw new NoteAlreadyExistsException();
        }
    }

    @Override
    public void update(Note note) {
        noteRepository.findById(note.getId()).ifPresent(noteEntity -> noteRepository.save(mapper.toEntity(note)));
    }

    @Override
    public void removeById(Integer id) {
        noteRepository.findById(id).ifPresent(noteEntity -> noteRepository.deleteById(id));
    }

    @Override
    public void removeByScheduleId(Integer scheduleId) {
        noteRepository.findByScheduleId(scheduleId).forEach(noteEntity -> noteRepository.deleteById(noteEntity.getId()));
    }
}
