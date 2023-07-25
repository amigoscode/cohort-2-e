package com.amigoscode.domain.note;

import com.amigoscode.BaseIT;
import com.amigoscode.TestScheduleFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.version.VersionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteServiceIT extends BaseIT {

    @Autowired
    com.amigoscode.domain.schedule.ScheduleService scheduleService;
    @Autowired
    VersionService versionService;
    @Autowired
    NoteService noteService;

    @Test
    void add_note_test() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule scheduleToSave = TestScheduleFactory.create();
        scheduleToSave.getVersion().setUpdatedBy(savedUser.getId());
        scheduleToSave.getNote().setCreatedBy(savedUser.getId());
        Schedule savedSchedule = scheduleService.save(scheduleToSave);
        Note savedNote = savedSchedule.getNote();

        //when
        Note readNote = noteService.findByScheduleIdAndVersion(savedNote.getScheduleId(), savedNote.getScheduleVersion());

        //then
        Assertions.assertEquals(savedNote.getNote(), readNote.getNote());
        Assertions.assertEquals(savedNote.getCreatedAt().toInstant(), readNote.getCreatedAt().toInstant());
    }

    @Test
    void get_by_id_should_return_correct_note() {
        //given
        User user1 = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user1);

        Schedule schedule1 = TestScheduleFactory.create();
        Schedule schedule2 = TestScheduleFactory.create();

        schedule1.getVersion().setUpdatedBy(savedUser.getId());
        schedule2.getVersion().setUpdatedBy(savedUser.getId());

        schedule1.getNote().setCreatedBy(savedUser.getId());
        schedule2.getNote().setCreatedBy(savedUser.getId());

        Schedule savedSchedule1 = scheduleService.save(schedule1);
        Schedule savedSchedule2 = scheduleService.save(schedule2);

        Note note1 = savedSchedule1.getNote();
        Note note2 = savedSchedule2.getNote();

        //when
        Note readNote = noteService.findByScheduleIdAndVersion(note1.getScheduleId(), note1.getScheduleVersion());

        //then
        Assertions.assertEquals(note1.getNote(), readNote.getNote());
        Assertions.assertEquals(note1.getScheduleVersion(), readNote.getScheduleVersion());
    }

}