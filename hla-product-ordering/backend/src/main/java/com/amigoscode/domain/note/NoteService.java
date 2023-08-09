package com.amigoscode.domain.note;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;

@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public Note findByScheduleIdAndVersion(Integer scheduleId, Integer version){
        return noteRepository.findByScheduleIdAndVersion(scheduleId, version).
                orElseThrow(NoteNotFoundException::new);
    }

    public Note findById(Integer id){
        return noteRepository.findById(id).
                orElseThrow(NoteNotFoundException::new);
    }

    public PageNote findAll(Pageable pageable) { return noteRepository.findAll(pageable); }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void update(Note note){
        noteRepository.update(note);
    }

    public void removeById(Integer id){
        noteRepository.removeById(id);
    }
    public void removeByScheduleId(Integer scheduleId){
        noteRepository.removeByScheduleId(scheduleId);
    }

}
