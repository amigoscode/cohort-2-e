package com.amigoscode.external.storage.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScheduleRepository extends JpaRepository<ScheduleEntity,Integer> {

}
