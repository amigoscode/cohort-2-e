package com.amigoscode.api.schedule;

import com.amigoscode.BaseIT;
import com.amigoscode.TestScheduleFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.api.response.ErrorResponse;
import com.amigoscode.api.response.MessageResponse;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleService;
import com.amigoscode.domain.schedule.Status;
import com.amigoscode.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScheduleControllerIT extends BaseIT {
    @Autowired
    ScheduleService service;

    @Test
    void admin_should_get_information_about_any_schedule() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();
        Schedule savedSchedule = service.save(schedule, savedUser.getId());
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/schedules/" + savedSchedule.getId(),
                token,
                null,
                ScheduleDto.class);

        //then
        ScheduleDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(body);
        assertEquals(schedule.getPatientId(), body.patientId());
        assertEquals(schedule.getStatus(), body.status());
    }

    @Test
    void admin_should_get_response_code_404_when_schedule_not_exits_in_db() {
        //given
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/schedules/10",
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void admin_should_get_response_code_conflict_when_schedule_is_in_db() {
        //given

        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();

        Schedule savedSchedule = service.save(schedule, savedUser.getId());
        String adminToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/schedules",
                adminToken,
                savedSchedule,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }


    @Test
    void admin_should_be_able_to_save_new_schedule() {
        //given

        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();
        schedule.getNote().setCreatedBy(savedUser.getId());
        schedule.getVersion().setUpdatedBy(savedUser.getId());
        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/schedules",
                adminAccessToken,
                schedule,
                ScheduleDto.class);

        //then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        //and
        ScheduleDto body = response.getBody();
        assertNotNull(body);
        assertEquals(body.patientId(), schedule.getPatientId());
        assertEquals(body.status(), schedule.getStatus());
    }


    @Test
    void admin_should_be_able_to_update_schedule() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();
        Schedule savedSchedule = service.save(schedule, savedUser.getId());

//        System.out.println("####### savedSchedule: " + savedSchedule);


        Schedule toUpdate = new Schedule(
                savedSchedule.getId(),
                2,
                Status.REVIEW,
                savedSchedule.getVersion(),
                savedSchedule.getNote()
        );

//        System.out.println("####### toUpdate: " + toUpdate);

        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/schedules",
                adminAccessToken,
                toUpdate,
                ScheduleDto.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void admin_should_be_get_response_code_404_when_update_schedule_not_exits() {
        //given
        String token = getAccessTokenForAdmin();
        Schedule fakeSchedule = TestScheduleFactory.create();

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/schedules",
                token,
                fakeSchedule,
                MessageResponse.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_schedule() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();
        Schedule savedSchedule = service.save(schedule, savedUser.getId());

        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/schedules/" + savedSchedule.getId(),
                adminAccessToken,
                null,
                ScheduleDto.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void admin_should_get_response_code_204_when_schedule_not_exists() {
        //given
//        Schedule schedule = TestScheduleFactory.create();
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/schedules/" + 10,
                token,
                null,
                MessageResponse.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    void admin_should_get_pageable_list_of_schedules() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();
        service.save(schedule, savedUser.getId());

        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/schedules",
                adminAccessToken,
                null,
                PageScheduleDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageScheduleDto body = response.getBody();
        //and
        assertNotNull(body);
        assertEquals(1, body.schedules().size());
        assertEquals(1, body.totalElements());
        assertEquals(1, body.totalPages());
        assertEquals(1, body.currentPage());
    }

}
