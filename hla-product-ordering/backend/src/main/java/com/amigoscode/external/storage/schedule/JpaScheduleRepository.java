package com.amigoscode.external.storage.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaScheduleRepository extends JpaRepository<ScheduleEntity,Integer> {
    Optional<ScheduleEntity>  findByPatientId(Integer patientId);
    


}
