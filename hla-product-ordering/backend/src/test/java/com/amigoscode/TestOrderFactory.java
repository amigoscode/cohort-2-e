package com.amigoscode;


import com.amigoscode.domain.order.Order;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestOrderFactory {

    private static int orderSequence = 10;

    public static Order create() {
        orderSequence++;
        return new Order(
                orderSequence++,
                orderSequence++,
                orderSequence++,
                ZonedDateTime.of(2023, 6, 24, 12, 40, 00, 0, ZoneId.of("UTC")),
                orderSequence++,
                ZonedDateTime.of(2023, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC"))
        );
    }
}
