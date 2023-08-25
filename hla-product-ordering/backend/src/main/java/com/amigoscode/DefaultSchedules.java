package com.amigoscode;

import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleService;
import com.amigoscode.domain.schedule.Status;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log
public class DefaultSchedules implements CommandLineRunner {

    private final ScheduleService scheduleService;
    private final PatientService patientService;
    private static Integer id = 0;

    public DefaultSchedules(ScheduleService scheduleService, PatientService patientService){
        this.scheduleService = scheduleService;
        this.patientService = patientService;
    }

    private final Schedule schedule = new Schedule(
            null,
            null,
            Status.REVIEW
    );

    @Override
    public void run(String... args) throws Exception {
        addSchedules();
    }

    private void addSchedules() {
        List<Patient> patients =  patientService.findAll();
        patients.forEach(patient -> {
            id++;
            scheduleService.save(new Schedule(
                    null,
                    patient.getId(),
                    Status.REVIEW
            ), 1);
        });
    }
}
