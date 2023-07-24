package com.amigoscode.domain.schedule;

import com.amigoscode.BaseIT;
import com.amigoscode.TestScheduleFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleServiceIT extends BaseIT {

    @Autowired
    ScheduleService scheduleService;

    @Test
    void add_schedule_test() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule scheduleToSave = TestScheduleFactory.create();
        scheduleToSave.getVersion().setUpdatedBy(savedUser.getId());
        scheduleToSave.getNote().setCreatedBy(savedUser.getId());
        Schedule savedSchedule = scheduleService.save(scheduleToSave);
        //when
        Schedule readSchedule = scheduleService.findById(savedSchedule.getId());

        //then
        Assertions.assertEquals(savedSchedule.getVersion(), readSchedule.getVersion());
        Assertions.assertEquals(savedSchedule.getNote(), readSchedule.getNote());
        Assertions.assertEquals(savedSchedule.getPatientId(), readSchedule.getPatientId());
    }

    @Test
    void get_by_id_should_return_correct_schedule() {
        //given
        User user1 = TestUserFactory.createTechnologist();
        userService.save(user1);

        Schedule schedule1 = TestScheduleFactory.create();
        Schedule schedule2 = TestScheduleFactory.create();

        schedule1.getVersion().setUpdatedBy(user1.getId());
        schedule2.getVersion().setUpdatedBy(user1.getId());

        schedule1.getNote().setCreatedBy(user1.getId());
        schedule2.getNote().setCreatedBy(user1.getId());

        Schedule savedSchedule1 = scheduleService.save(schedule1);
        Schedule savedSchedule2 = scheduleService.save(schedule2);


        //when
        Schedule readSchedule = scheduleService.findById(savedSchedule1.getId());

        //then
        Assertions.assertEquals(savedSchedule1.getVersion(), readSchedule.getVersion());
        Assertions.assertEquals(savedSchedule1.getNote(), readSchedule.getNote());
        Assertions.assertEquals(savedSchedule1.getPatientId(), readSchedule.getPatientId());
        Assertions.assertEquals(savedSchedule1.getStatus(), readSchedule.getStatus());
    }

}