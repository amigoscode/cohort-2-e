package com.amigoscode.domain.patient;

import com.amigoscode.BaseIT;
import com.amigoscode.TestPatientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class PatientServiceIT extends BaseIT {

    @Autowired
    PatientService service;

    @Test
    void add_patient_test() {
        //given
        Patient patient = TestPatientFactory.create();
        Patient savedPatient = service.save(patient);

        //when
        Patient readPatient = service.findById(savedPatient.getId());

        //then
        Assertions.assertEquals(patient.getMrn(), readPatient.getMrn());
        Assertions.assertEquals(patient.getDob().toInstant(), readPatient.getDob().toInstant());
    }

    @Test
    void find_by_id_should_return_correct_patient() {
        //given
        Patient patient1 = TestPatientFactory.create();
        Patient patient2 = TestPatientFactory.create();
        Patient patient3 = TestPatientFactory.create();

        Patient savedPatient1 = service.save(patient1);
        Patient savedPatient2 = service.save(patient2);
        Patient savedPatient3 = service.save(patient3);


        //when
        Patient readPatient = service.findById(savedPatient2.getId());

        //then
        Assertions.assertEquals(patient2.getMrn(), readPatient.getMrn());
        Assertions.assertEquals(patient2.getDob().toInstant(), readPatient.getDob().toInstant());
    }

    @Test
    void find_all_should_return_list_of_patients() {
        //given
        Patient patient1 = TestPatientFactory.create();
        Patient patient2 = TestPatientFactory.create();
        Patient patient3 = TestPatientFactory.create();

        Patient savedPatient1 = service.save(patient1);
        Patient savedPatient2 = service.save(patient2);
        Patient savedPatient3 = service.save(patient3);


        //when
        List<Patient> readPatients = service.findAll();

        //then
        Assertions.assertEquals(3, readPatients.size());
    }

}