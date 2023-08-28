package com.amigoscode.domain.version;

import com.amigoscode.BaseIT;
import com.amigoscode.TestPatientFactory;
import com.amigoscode.TestScheduleFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.note.NoteService;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientService;
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

    @Autowired
    PatientService patientService;

    @Test
    void add_version_test() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule scheduleToSave = TestScheduleFactory.create();
        Schedule savedSchedule = scheduleService.save(scheduleToSave, savedUser.getId());
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
        Patient patient = TestPatientFactory.create();
        Patient savedPatient = patientService.save(patient);

        Schedule schedule1 = TestScheduleFactory.create();
        schedule1.setPatient(savedPatient);
        schedule1.setPatientId(savedPatient.getId());
        Schedule schedule2 = TestScheduleFactory.create();
        schedule2.setPatient(savedPatient);
        schedule2.setPatientId(savedPatient.getId());

        Schedule savedSchedule1 = scheduleService.save(schedule1, savedUser.getId());
        Schedule savedSchedule2 = scheduleService.save(schedule2, savedUser.getId());

        Version version1 = savedSchedule1.getVersion();
        Version version2 = savedSchedule2.getVersion();

        //when
        Version readVersion = versionService.findById(version1.getId(), version1.getScheduleId());

        //then
        Assertions.assertEquals(version1.getVersion(), readVersion.getVersion());
        Assertions.assertEquals(version1.getQuantity(), readVersion.getQuantity());
    }

}