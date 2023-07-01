package com.amigoscode.api.auth;

import com.amigoscode.BaseIT;
import com.amigoscode.TestUserFactory;
import com.amigoscode.api.response.ErrorResponse;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationControllerIT extends BaseIT {

    @Autowired
    UserService userService;

    @Test
    void user_should_get_response_code_not_found_when_user_has_no_access() {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("terrence","password");

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/auth/login",
                null,
                authenticationRequest,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void user_should_get_response_code_success_when_user_has_access() {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("admin@gmail.com","password");

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/auth/login",
                null,
                authenticationRequest,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_get_response_code_success_when_user_has_access_and_is_a_technologist() {
        //given

        User user1 = TestUserFactory.createTechnologist();
        userService.save(user1);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(user1.getEmail(),user1.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/auth/login",
                null,
                authenticationRequest,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_get_response_code_success_when_user_has_access_and_is_a_medical_doctor() {
        //given

        User user1 = TestUserFactory.createMedicalDoctor();
        userService.save(user1);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(user1.getEmail(),user1.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/auth/login",
                null,
                authenticationRequest,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void user_should_get_response_code_forbidden_when_password_is_incorrect() {
        //given

        User user1 = TestUserFactory.createTechnologist();
        userService.save(user1);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(user1.getEmail(),"bad_password");

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/auth/login",
                null,
                authenticationRequest,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}