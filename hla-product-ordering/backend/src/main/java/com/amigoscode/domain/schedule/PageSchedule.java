package com.amigoscode.domain.schedule;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public record PageSchedule(List<Schedule> schedules, Integer currentPage, Integer totalPages,
                           Long totalElements) implements Serializable {

}
