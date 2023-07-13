package com.amigoscode.api.patient;

import com.amigoscode.BaseIT;
import com.amigoscode.TestPatientFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.api.response.ErrorResponse;
import com.amigoscode.api.user.PageUserDto;
import com.amigoscode.api.user.UserDto;
import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientControllerIT extends BaseIT {

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @Test
    void user_should_get_information_about_any_patient() {
        //given
        User user = userService.save(TestUserFactory.createTechnologist());
        String token = getAccessTokenForUser(user);

        Patient patient = patientService.save(TestPatientFactory.create());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/patients/" + patient.getId(),
                token,
                null,
                PatientDto.class);

        //then
        PatientDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(body);
        assertEquals(patient.getId(), body.id());
        assertEquals(patient.getMrn(), body.mrn());
        assertEquals(patient.getDob().toInstant(), body.dob().toInstant());
        assertEquals(patient.getCreatedAt().toInstant(), body.createdAt().toInstant());
    }

    @Test
    void user_should_get_response_code_404_when_patient_not_exits_in_db() {
        //given
        User user = userService.save(TestUserFactory.createTechnologist());
        String token = getAccessTokenForUser(user);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/patients/10",
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void user_should_get_pageable_list_of_patients() {
        //give
        User user = userService.save(TestUserFactory.createTechnologist());
        String token = getAccessTokenForUser(user);

        Patient patient = patientService.save(TestPatientFactory.create());

        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/patients",
                token,
                null,
                PagePatientDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PagePatientDto body = response.getBody();
        //and
        assertNotNull(body);
        assertEquals(1, body.patients().size());
        assertEquals(1, body.totalElements());
        assertEquals(1, body.totalPages());
        assertEquals(1, body.currentPage());
    }

}
