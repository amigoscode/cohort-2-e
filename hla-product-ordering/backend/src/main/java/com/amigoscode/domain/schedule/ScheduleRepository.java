package com.amigoscode.domain.schedule;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ScheduleRepository {
    Optional<Schedule> findById(Integer id);

    PageSchedule findAll(Pageable pageable);

    Schedule save(Schedule schedule);

    void update(Schedule schedule);

    void removeById(Integer id);
    Optional<Schedule> findByPatientId(Integer patientId);




}
