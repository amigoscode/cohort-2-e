package com.amigoscode.domain.schedule;

import com.amigoscode.BaseIT;
import com.amigoscode.TestPatientFactory;
import com.amigoscode.TestScheduleFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

public class ScheduleServiceIT extends BaseIT {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PatientService patientService;

    @Test
    void add_schedule_test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);

        Patient patient = TestPatientFactory.create();
        Patient savedPatient = patientService.save(patient);

        Schedule scheduleToSave = TestScheduleFactory.create();
        scheduleToSave.setPatientId(savedPatient.getId());
        Schedule savedSchedule = scheduleService.save(scheduleToSave, savedUser.getId());
        //when
        Schedule readSchedule = scheduleService.findById(savedSchedule.getId());

        //then
        Assertions.assertEquals(savedSchedule.getVersion().getId(), readSchedule.getVersion().getId());
        Assertions.assertEquals(savedSchedule.getVersion().getUpdatedAt().format(formatter), readSchedule.getVersion().getUpdatedAt().format(formatter));
        Assertions.assertEquals(savedSchedule.getNote().getId(), readSchedule.getNote().getId());
        Assertions.assertEquals(savedSchedule.getNote().getCreatedAt().format(formatter), readSchedule.getNote().getCreatedAt().format(formatter));
        Assertions.assertEquals(savedSchedule.getPatientId(), readSchedule.getPatientId());
    }

    @Test
    void get_by_id_should_return_correct_schedule() throws InterruptedException {
        //given
        User user1 = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user1);

        Patient patient = TestPatientFactory.create();
        Patient savedPatient = patientService.save(patient);

        Schedule schedule1 = TestScheduleFactory.create();
        schedule1.setPatientId(savedPatient.getId());
        Schedule schedule2 = TestScheduleFactory.create();
        schedule2.setPatientId(savedPatient.getId());
        Schedule schedule3 = TestScheduleFactory.create();
        schedule3.setPatientId(savedPatient.getId());

        Schedule savedSchedule1 = scheduleService.save(schedule1, savedUser.getId());
        Schedule savedSchedule2 = scheduleService.save(schedule2, savedUser.getId());
        Schedule savedSchedule3 = scheduleService.save(schedule3, savedUser.getId());

        //when
        Schedule readSchedule = scheduleService.findById(savedSchedule2.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //then
        Assertions.assertEquals(savedSchedule2.getVersion().getId(), readSchedule.getVersion().getId());
        Assertions.assertEquals(savedSchedule2.getVersion().getUpdatedAt().format(formatter), readSchedule.getVersion().getUpdatedAt().format(formatter));
        Assertions.assertEquals(savedSchedule2.getNote().getId(), readSchedule.getNote().getId());
        Assertions.assertEquals(savedSchedule2.getNote().getCreatedAt().format(formatter), readSchedule.getNote().getCreatedAt().format(formatter));
        Assertions.assertEquals(savedSchedule2.getPatientId(), readSchedule.getPatientId());
        Assertions.assertEquals(savedSchedule2.getStatus(), readSchedule.getStatus());
    }

    @Test
    void update_schedule_test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Patient patient = TestPatientFactory.create();
        Patient savedPatient = patientService.save(patient);

        Schedule scheduleToSave = TestScheduleFactory.create();
        scheduleToSave.setPatientId(savedPatient.getId());
        Schedule savedSchedule = scheduleService.save(scheduleToSave, savedUser.getId());
        Schedule scheduleToUpdate = new Schedule(
                savedSchedule.getId(),
                savedPatient.getId(),
                Status.REVIEW,
                savedSchedule.getVersion(),
                savedSchedule.getNote()
        );
        //when
        scheduleService.update(scheduleToUpdate, savedUser.getId());
        Schedule readSchedule = scheduleService.findById(savedSchedule.getId());

        //then
        Assertions.assertEquals(patient.getId(), readSchedule.getPatientId());
        Assertions.assertEquals(scheduleToUpdate.getVersion().getUpdatedAt().format(formatter), readSchedule.getVersion().getUpdatedAt().format(formatter));
        Assertions.assertEquals(scheduleToUpdate.getNote().getId(), readSchedule.getNote().getId());
        Assertions.assertEquals(scheduleToUpdate.getNote().getCreatedAt().format(formatter), readSchedule.getNote().getCreatedAt().format(formatter));
        Assertions.assertEquals(scheduleToUpdate.getPatientId(), readSchedule.getPatientId());
    }

}