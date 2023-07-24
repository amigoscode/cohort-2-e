package com.amigoscode.domain.note;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private static ZonedDateTime scheduledFor = ZonedDateTime.of(
            2023,
            6,
            17,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );

    private static ZonedDateTime received = ZonedDateTime.of(
            2023,
            6,
            16,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );
    private final Note note = new Note(
            1,
            3,
            4,
            "This is a note",
            scheduledFor,
            5
    );


    @Test
    void find_by_id_method_should_return_founded_note_when_note_exist() {
        Mockito.when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        //when
        Note foundedNote = noteService.findById(note.getId());

        //then
        Assertions.assertNotNull(foundedNote);
        Assertions.assertEquals(note.getId(), foundedNote.getId());
        Assertions.assertEquals(note.getScheduleId(), foundedNote.getScheduleId());
        Assertions.assertEquals(note.getScheduleVersion(), foundedNote.getScheduleVersion());
        Assertions.assertEquals(note.getNote(), foundedNote.getNote());
        Assertions.assertEquals(note.getCreatedAt(), foundedNote.getCreatedAt());
        Assertions.assertEquals(note.getCreatedBy(), foundedNote.getCreatedBy());
    }

    @Test
    void find_by_id_method_should_throw_note_not_found_exception_when_note_does_not_exist() {
        Mockito.when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(NoteNotFoundException.class,
                () -> noteService.findById(note.getId()));
    }

    @Test
    void update_method_should_not_throw_exception() {
        Assertions.assertDoesNotThrow(() -> noteService.update(note));
    }


    @Test
    void delete_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> noteService.removeById(note.getId()));
    }

    @Test
    void save_method_should_return_saved_note_when_note_does_not_exist() {
        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(note);
        //when
        Note savedNote = noteService.save(note);
        //then
        Assertions.assertNotNull(savedNote);
        Assertions.assertEquals(note.getId(), savedNote.getId());
        Assertions.assertEquals(note.getScheduleId(), savedNote.getScheduleId());
        Assertions.assertEquals(note.getScheduleVersion(), savedNote.getScheduleVersion());
        Assertions.assertEquals(note.getNote(), savedNote.getNote());
        Assertions.assertEquals(note.getCreatedAt(), savedNote.getCreatedAt());
        Assertions.assertEquals(note.getCreatedBy(), savedNote.getCreatedBy());
    }
}
