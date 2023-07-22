package com.amigoscode.external.storage.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @SequenceGenerator(
            name = "order_id_seq",
            sequenceName = "order_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private Integer scheduleId;
    @Column(
            nullable = false
    )
    private Integer scheduleVersion;
    @Column(
            nullable = false
    )
    private ZonedDateTime scheduledFor;

    @Column(
            nullable = true
    )
    private Integer emailId;
    @Column(
            nullable = false
    )
    private ZonedDateTime received;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(scheduleId, that.scheduleId) && Objects.equals(scheduleVersion, that.scheduleVersion) && Objects.equals(scheduledFor, that.scheduledFor) && Objects.equals(emailId, that.emailId) && Objects.equals(received, that.received);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleId, scheduleVersion, scheduledFor, emailId, received);
    }
}
