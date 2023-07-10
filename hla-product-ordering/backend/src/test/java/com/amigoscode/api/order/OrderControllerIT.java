package com.amigoscode.api.order;

import com.amigoscode.BaseIT;
import com.amigoscode.TestOrderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.api.response.ErrorResponse;
import com.amigoscode.api.user.PageUserDto;
import com.amigoscode.api.user.UserDto;
import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderService;
import com.amigoscode.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerIT extends BaseIT {

    @Autowired
    OrderService service;

    @Test
    void admin_should_get_information_about_any_order() {
        //given
        Order order = TestOrderFactory.create();
        Order savedOrder = service.save(order);
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/orders/" + savedOrder.getId(),
                token,
                null,
                OrderDto.class);

        //then
        OrderDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(body);
        assertEquals(order.getScheduleId(), body.scheduleId());
        assertEquals(order.getScheduleVersion(), body.scheduleVersion());
        assertEquals(order.getScheduledFor().toInstant(), body.scheduledFor().toInstant());
        assertEquals(order.getEmailId(), body.emailId());

    }


    @Test
    void admin_should_get_response_code_404_when_order_not_exits_in_db() {
        //given
        String token = getAccessTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/orders/10",
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }


    @Test
    void admin_should_get_pageable_list_of_orders() {
        //given
        Order order = TestOrderFactory.create();
        String adminAccessToken = getAccessTokenForAdmin();
        service.save(order);

        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/orders",
                adminAccessToken,
                null,
                PageOrderDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageOrderDto body = response.getBody();
        //and
        assertNotNull(body);
        assertEquals(1, body.orders().size());
        assertEquals(1, body.totalElements());
        assertEquals(1, body.totalPages());
        assertEquals(1, body.currentPage());
    }

}
