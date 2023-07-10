package com.amigoscode.order;

import com.amigoscode.BaseIT;
import com.amigoscode.TestOrderFactory;
import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceIT extends BaseIT {

    @Autowired
    OrderService service;

    @Test
    void get_by_id_should_return_correct_order() {
        //given
        Order order1 = TestOrderFactory.create();
        Order order2 = TestOrderFactory.create();
        Order order3 = TestOrderFactory.create();

        Order savedOrder1 = service.save(order1);
        Order savedOrder2 = service.save(order2);
        Order savedOrder3 = service.save(order3);

        //when
        Order readOrder = service.findById(savedOrder2.getId());

        //then
        Assertions.assertEquals(order2.getScheduleId(), readOrder.getScheduleId());
        Assertions.assertEquals(order2.getScheduleVersion(), readOrder.getScheduleVersion());
        Assertions.assertEquals(order2.getScheduledFor().toInstant(), readOrder.getScheduledFor().toInstant());
        Assertions.assertEquals(order2.getEmailId(), readOrder.getEmailId());
    }
}