package com.amigoscode.api.email;

import com.amigoscode.BaseIT;
import com.amigoscode.TestEmailFactory;
import com.amigoscode.TestOrderFactory;
import com.amigoscode.TestProviderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.appservices.EmailApplicationService;
import com.amigoscode.appservices.IAuthenticationFacade;
import com.amigoscode.appservices.ProviderApplicationService;
import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderService;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmailControllerIT extends BaseIT {

    @MockBean
    JavaMailSender mailSender;

    @MockBean
    IAuthenticationFacade authenticationFacade;

    @Autowired
    EmailApplicationService emailApplicationService;

    @Autowired
    UserService userService;

    @Autowired
    ProviderApplicationService providerService;

    @Autowired
    OrderService orderService;

    @Test
    void user_should_be_able_to_send_email() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        String token = getAccessTokenForUser(savedUser);
        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        provider.setEmail("attwosix@gmail.com");
        Mockito.when(authenticationFacade.getLoggedInUserId()).thenReturn(savedUser.getId());
        Provider savedProvider = providerService.save(provider);
        Order order1 = orderService.save(TestOrderFactory.create());
        Order order2 = orderService.save(TestOrderFactory.create());
        Order order3 = orderService.save(TestOrderFactory.create());
        Order order4 = TestOrderFactory.create();
        order4.setEmailId(1);
        Order savedOrder4 = orderService.save(order4);

        Email email = TestEmailFactory.create();
        email.setUserId(savedUser.getId());
        email.setProviderId(savedProvider.getId());
        email.setOrders(List.of(order1.getId(), order2.getId(), order3.getId(), savedOrder4.getId()));

        //when
        var response = callHttpMethod(
                HttpMethod.POST,
                "/api/v1/emails",
                token,
                email,
                EmailDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        EmailDto body = response.getBody();
        Mockito.verify(mailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
        Assertions.assertNotNull(body.userId());
        Assertions.assertEquals(body.orders(), List.of(
                order1.getId(), order2.getId(), order3.getId()
        ));
    }

    @Test
    void user_should_get_information_about_any_email() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        String token = getAccessTokenForUser(savedUser);
        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        provider.setEmail("attwosix@gmail.com");
        Mockito.when(authenticationFacade.getLoggedInUserId()).thenReturn(savedUser.getId());
        Provider savedProvider = providerService.save(provider);
        Order order1 = orderService.save(TestOrderFactory.create());
        Order order2 = orderService.save(TestOrderFactory.create());
        Order order3 = orderService.save(TestOrderFactory.create());

        Email email = TestEmailFactory.create();
        email.setUserId(savedUser.getId());
        email.setProviderId(savedProvider.getId());
        email.setOrders(List.of(order1.getId(), order2.getId(), order3.getId()));

        Email savedEmail = emailApplicationService.save(email);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/emails/" + savedEmail.getId(),
                token,
                null,
                EmailDto.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        EmailDto body = response.getBody();
        Assertions.assertNotNull(body.userId());
        Assertions.assertEquals(body.orders(), List.of(
                order1.getId(), order2.getId(), order3.getId()
        ));

    }

    @Test
    void admin_should_get_pageable_list_of_orders() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        String token = getAccessTokenForUser(savedUser);
        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        provider.setEmail("attwosix@gmail.com");
        Mockito.when(authenticationFacade.getLoggedInUserId()).thenReturn(savedUser.getId());
        Provider savedProvider = providerService.save(provider);
        Order order1 = orderService.save(TestOrderFactory.create());
        Order order2 = orderService.save(TestOrderFactory.create());
        Order order3 = orderService.save(TestOrderFactory.create());

        Email email = TestEmailFactory.create();
        email.setUserId(savedUser.getId());
        email.setProviderId(savedProvider.getId());
        email.setOrders(List.of(order1.getId(), order2.getId(), order3.getId()));

        Email savedEmail = emailApplicationService.save(email);

        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/emails",
                token,
                null,
                PageEmailDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageEmailDto body = response.getBody();
        //and
        assertNotNull(body);
        assertEquals(1, body.emails().size());
        assertEquals(1, body.totalElements());
        assertEquals(1, body.totalPages());
        assertEquals(1, body.currentPage());
    }

}