package com.amigoscode.external.storage.email;

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
public class EmailEntity {

    @Id
    @SequenceGenerator(
            name = "email_id_seq",
            sequenceName = "email_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "email_id_seq"
    )
    private Integer id;


    @Column(
            nullable = false
    )
    private Integer ProviderId;

    @Column(
            nullable = false
    )
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime sentAt;

    @Column
    private Integer scheduleId;

    @Column(
            nullable = false
    )
    private Integer UserId;

    @Column(
            nullable = false
    )
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailEntity that = (EmailEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
