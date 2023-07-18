package com.amigoscode.domain.order;

import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderNotFoundException;
import com.amigoscode.domain.order.OrderRepository;
import com.amigoscode.domain.order.OrderService;

import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private static ZonedDateTime scheduledFor = ZonedDateTime.of(
            2023,
            6,
            17,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );

    private static ZonedDateTime received = ZonedDateTime.of(
            2023,
            6,
            16,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );
    private final Order fakeOrder = new Order(
            1,
            3,
            4,
            scheduledFor,
            5,
            received
    );




    @Test
    void find_by_id_method_should_return_founded_order_when_order_exist() {
        Mockito.when(orderRepository.findById(fakeOrder.getId())).thenReturn(Optional.of(fakeOrder));

        //when
        Order foundedOrder = orderService.findById(fakeOrder.getId());

        //then
        Assertions.assertNotNull(foundedOrder);
        Assertions.assertEquals(fakeOrder.getId(), foundedOrder.getId());
        Assertions.assertEquals(fakeOrder.getScheduleId(), foundedOrder.getScheduleId());
        Assertions.assertEquals(fakeOrder.getScheduleVersion(), foundedOrder.getScheduleVersion());
        Assertions.assertEquals(fakeOrder.getScheduledFor(), foundedOrder.getScheduledFor());
        Assertions.assertEquals(fakeOrder.getEmailId(), foundedOrder.getEmailId());
    }

    @Test
    void find_by_id_method_should_throw_order_not_found_exception_when_order_does_not_exist() {
        Mockito.when(orderRepository.findById(fakeOrder.getId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(OrderNotFoundException.class,
                ()-> orderService.findById(fakeOrder.getId()));
    }

    @Test
    void update_method_should_not_throw_exception() {
        Assertions.assertDoesNotThrow(() -> orderService.update(fakeOrder));
    }


    @Test
    void delete_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> orderService.removeById(fakeOrder.getId()));
    }

    @Test
    void save_method_should_return_saved_user_when_order_does_not_exist() {
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(fakeOrder);

        //when
        Order savedOrder = orderService.save(fakeOrder);

        //then
        Assertions.assertNotNull(savedOrder);
        Assertions.assertEquals(fakeOrder.getId(), savedOrder.getId());
        Assertions.assertEquals(fakeOrder.getScheduleId(), savedOrder.getScheduleId());
        Assertions.assertEquals(fakeOrder.getScheduleVersion(), savedOrder.getScheduleVersion());
        Assertions.assertEquals(fakeOrder.getScheduledFor(), savedOrder.getScheduledFor());
        Assertions.assertEquals(fakeOrder.getEmailId(), savedOrder.getEmailId());
    }

    @Test
    void save_method_should_throw_user_already_exist_exception_when_order_exist() {
        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenThrow(new OrderAlreadyExistsException());

        //then
        Assertions.assertThrows(OrderAlreadyExistsException.class,
                ()-> orderService.save(fakeOrder));
    }

}