package com.amigoscode.domain.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Email {

    private Integer id;
    private Integer providerId;
    private ZonedDateTime createdAt;
    private Integer userId;
    private String content;
    private List<Integer> orders;

}
