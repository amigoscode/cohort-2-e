package com.amigoscode.api.version;

import com.amigoscode.BaseIT;
import com.amigoscode.TestScheduleFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.ScheduleService;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.version.Version;
import com.amigoscode.domain.version.VersionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VersionControllerIT extends BaseIT {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    VersionService versionService;


    @Test
    void admin_should_get_pageable_list_of_schedule_versions() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();
        schedule.getNote().setCreatedBy(savedUser.getId());
        schedule.getVersion().setUpdatedBy(savedUser.getId());
        Schedule savedSchedule = scheduleService.save(schedule);


        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/schedules/"
                        + savedSchedule.getId()
                        + "/versions",
                adminAccessToken,
                null,
                PageVersionDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageVersionDto body = response.getBody();
        //and
        assertNotNull(body);
        assertEquals(1, body.versions().size());
        assertEquals(1, body.totalElements());
        assertEquals(1, body.totalPages());
        assertEquals(1, body.currentPage());
    }

    @Test
    void admin_should_get_information_about_any_schedule_version() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Schedule schedule = TestScheduleFactory.create();
        schedule.getNote().setCreatedBy(savedUser.getId());
        schedule.getVersion().setUpdatedBy(savedUser.getId());

        Schedule savedSchedule = scheduleService.save(schedule);
        Version savedScheduleVersion = savedSchedule.getVersion();
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/schedules/"
                        + savedSchedule.getId()
                        + "/versions/"
                        + savedScheduleVersion.getId(),
                token,
                null,
                VersionDto.class);

        //then
        VersionDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(body);
        assertEquals(savedScheduleVersion.getVersion(), body.version());
        assertEquals(savedScheduleVersion.getState(), body.state());
    }
}
