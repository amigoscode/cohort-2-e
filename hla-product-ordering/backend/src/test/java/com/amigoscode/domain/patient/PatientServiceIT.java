package com.amigoscode.domain.patient;

import com.amigoscode.BaseIT;
import com.amigoscode.TestPatientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Test
    void find_all_with_pagination_should_return_page_of_patients() {
        //given
        Patient patient1 = TestPatientFactory.create();
        Patient patient2 = TestPatientFactory.create();
        Patient patient3 = TestPatientFactory.create();
        Patient patient4 = TestPatientFactory.create();
        Patient patient5 = TestPatientFactory.create();

        Patient savedPatient1 = service.save(patient1);
        Patient savedPatient2 = service.save(patient2);
        Patient savedPatient3 = service.save(patient3);
        Patient savedPatient4 = service.save(patient4);
        Patient savedPatient5 = service.save(patient5);


        final int page = 0;
        final int size = 3;
        Pageable pageable = PageRequest.of(page, size);

        //when
        PagePatient pagePatient = service.findAll(pageable);

        //then
        Assertions.assertEquals(5, pagePatient.getTotalElements());
        Assertions.assertEquals(2, pagePatient.getTotalPages());
    }

    @Test
    void find_all_with_pagination_should_return_empty_page_if_any_patient_does_not_exist() {
        //given
        final int page = 0;
        final int size = 3;
        Pageable pageable = PageRequest.of(page, size);

        //when
        PagePatient pagePatient = service.findAll(pageable);

        //then
        Assertions.assertEquals(0, pagePatient.getTotalElements());
        Assertions.assertEquals(0, pagePatient.getTotalPages());
    }

}