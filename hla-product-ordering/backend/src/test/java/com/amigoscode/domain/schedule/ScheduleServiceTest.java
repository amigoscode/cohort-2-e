package com.amigoscode.domain.schedule;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.note.NoteService;
import com.amigoscode.domain.version.State;
import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionService;
import jakarta.persistence.MapKeyClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private VersionService versionService;
    @Mock
    private NoteService noteService;
    @InjectMocks
    private ScheduleService scheduleService;

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

    private final Schedule schedule = new Schedule(
            1,
            3,
            Status.REVIEW,
            new Version(
                    1,
                    3,
                    1,
                    1,
                    scheduledFor,
                    State.DONE,
                    scheduledFor,
                    scheduledFor,
                    5,
                    5
            ),
            new Note(
                    1,
                    1,
                    4,
                    "This is a note",
                    scheduledFor,
                    5
            )
    );
      private final Version version = new Version(
                    1,
                            3,
                            1,
                            1,
                    scheduledFor,
                    State.DONE,
                    scheduledFor,
                    scheduledFor,
                    5,
                            5
    );

      private  final Note note =  new Note(
              1,
              1,
              4,
              "This is a note",
              scheduledFor,
              5
      );


    @Test
    void find_by_id_method_should_return_founded_schedule_when_schedule_exist() {

        Mockito.when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        Mockito.when(versionService.findAllVersionsByScheduleId(schedule.getId())).thenReturn(List.of(version));
        Mockito.when(noteService.findByScheduleIdAndVersion(schedule.getId(), version.getVersion())).thenReturn(note);

        //when
        Schedule foundedSchedule = scheduleService.findById(schedule.getId());

        //then
        Assertions.assertNotNull(foundedSchedule);
        Assertions.assertEquals(schedule.getId(), foundedSchedule.getId());
        Assertions.assertEquals(schedule.getPatientId(), foundedSchedule.getPatientId());
        Assertions.assertEquals(schedule.getStatus(), foundedSchedule.getStatus());
        Assertions.assertEquals(schedule.getNote(), foundedSchedule.getNote());
        Assertions.assertEquals(schedule.getVersion(), foundedSchedule.getVersion());
    }

    @Test
    void find_by_id_method_should_throw_schedule_not_found_exception_when_schedule_does_not_exist() {
        Mockito.when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(ScheduleNotFoundException.class,
                () -> scheduleService.findById(schedule.getId()));
    }

//    @Test
//    void update_method_should_not_throw_exception() {
//        Assertions.assertDoesNotThrow(() -> scheduleService.update(schedule));
//    }


    @Test
    void delete_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> scheduleService.removeById(schedule.getId()));
    }

    @Test
    void save_method_should_return_saved_schedule_when_schedule_does_not_exist() {
        Mockito.when(scheduleRepository.save(Mockito.any(Schedule.class))).thenReturn(schedule);
        Mockito.when(scheduleRepository.save(schedule)).thenReturn(schedule);
        Mockito.when(versionService.save(schedule.getVersion())).thenReturn(schedule.getVersion());
        Mockito.when(noteService.save(schedule.getNote())).thenReturn(schedule.getNote());
        //when
        Schedule savedSchedule = scheduleService.save(schedule);
        //then
        Assertions.assertNotNull(savedSchedule);
        Assertions.assertEquals(schedule.getId(), savedSchedule.getId());
        Assertions.assertEquals(schedule.getPatientId(), savedSchedule.getPatientId());
        Assertions.assertEquals(schedule.getStatus(), savedSchedule.getStatus());
        Assertions.assertEquals(schedule.getNote(), savedSchedule.getNote());
        Assertions.assertEquals(schedule.getVersion(), savedSchedule.getVersion());
    }
}