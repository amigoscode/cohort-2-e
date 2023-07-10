package com.amigoscode.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class Order {

    private Integer id;
    private Integer scheduleId;
    private Integer scheduleVersion;
    private ZonedDateTime scheduledFor;
    private Integer emailId;
    private ZonedDateTime received;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(scheduleId, order.scheduleId) && Objects.equals(scheduleVersion, order.scheduleVersion) && Objects.equals(scheduledFor, order.scheduledFor) && Objects.equals(emailId, order.emailId) && Objects.equals(received, order.received);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleId, scheduleVersion, scheduledFor, emailId, received);
    }
}
