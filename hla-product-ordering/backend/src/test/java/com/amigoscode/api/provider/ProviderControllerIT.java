package com.amigoscode.api.provider;

import com.amigoscode.BaseIT;
import com.amigoscode.TestProviderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.api.response.ErrorResponse;
import com.amigoscode.api.response.MessageResponse;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProviderControllerIT extends BaseIT {

    @Autowired
    ProviderService service;

    @Test
    void admin_should_get_information_about_any_provider() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        Provider savedProvider = service.save(provider, savedUser.getId());
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/providers/" + savedProvider.getId(),
                token,
                null,
                ProviderDto.class);

        //then
        ProviderDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(body);
        assertEquals(provider.getEmail(), body.email());
        assertEquals(provider.getName(), body.name());
    }

    @Test
    void admin_should_get_response_code_404_when_provider_not_exits_in_db() {
        //given
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/providers/10",
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void admin_should_get_response_code_conflict_when_provider_is_in_db() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        Provider savedProvider = service.save(provider, savedUser.getId());
        String adminToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/providers",
                adminToken,
                provider,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }


    @Test
    void admin_should_be_able_to_save_new_provider() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/providers",
                adminAccessToken,
                provider,
                ProviderDto.class);

        //then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        //and
        ProviderDto body = response.getBody();
        assertNotNull(body);
        assertEquals(body.email(), provider.getEmail());
        assertEquals(body.name(), provider.getName());
    }




    @Test
    void admin_should_be_able_to_update_provider() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        Provider savedProvider = service.save(provider, savedUser.getId());
        Provider toUpdate = new Provider(
                provider.getId(),
                "Cody",
                "cody@gmail.com",
                provider.getCreatedAt(),
                provider.getCreatedBy()
        );

        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/providers",
                adminAccessToken,
                toUpdate,
                ProviderDto.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void admin_should_be_get_response_code_200_when_update_provider_not_exits() {
        //given
        String token = getAccessTokenForAdmin();
        Provider fakeProvider = TestProviderFactory.create();

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/providers",
                token,
                fakeProvider,
                MessageResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_provider() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        Provider savedProvider = service.save(provider, savedUser.getId());
        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/providers/" + savedProvider.getId(),
                adminAccessToken,
                null,
                ProviderDto.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void admin_should_get_response_code_204_when_provider_not_exists() {
        //given
        Provider provider = TestProviderFactory.create();
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/providers/" + provider.getId(),
                token,
                null,
                MessageResponse.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    void admin_should_get_pageable_list_of_providers() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        Provider savedProvider = service.save(provider, savedUser.getId());
        String adminAccessToken = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/providers",
                adminAccessToken,
                null,
                PageProviderDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageProviderDto body = response.getBody();
        //and
        assertNotNull(body);
        assertEquals(1, body.providers().size());
        assertEquals(1, body.totalElements());
        assertEquals(1, body.totalPages());
        assertEquals(1, body.currentPage());
    }

}
