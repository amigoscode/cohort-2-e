package com.amigoscode.domain.version;

import com.amigoscode.BaseIT;
import com.amigoscode.TestScheduleFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.note.NoteService;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleService;
import com.amigoscode.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VersionServiceIT extends BaseIT {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    VersionService versionService;

    @Test
    void add_version_test() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule scheduleToSave = TestScheduleFactory.create();
        scheduleToSave.getVersion().setUpdatedBy(savedUser.getId());
        scheduleToSave.getNote().setCreatedBy(savedUser.getId());
        Schedule savedSchedule = scheduleService.save(scheduleToSave);
        Version savedVersion = savedSchedule.getVersion();

        //when
        Version readVersion = versionService.findById(savedVersion.getId(), savedVersion.getScheduleId());

        //then
        Assertions.assertEquals(savedVersion.getVersion(), readVersion.getVersion());
        Assertions.assertEquals(savedVersion.getQuantity(), readVersion.getQuantity());
    }

    @Test
    void get_by_id_should_return_correct_version() {
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

        Version version1 = savedSchedule1.getVersion();
        Version version2 = savedSchedule2.getVersion();

        //when
        Version readVersion = versionService.findById(version1.getId(), version1.getScheduleId());

        //then
        Assertions.assertEquals(version1.getVersion(), readVersion.getVersion());
        Assertions.assertEquals(version1.getQuantity(), readVersion.getQuantity());
    }

}