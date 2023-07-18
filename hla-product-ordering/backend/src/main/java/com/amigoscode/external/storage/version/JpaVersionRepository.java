package com.amigoscode.external.storage.version;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaVersionRepository extends JpaRepository<VersionEntity, Integer> {

    Optional<VersionEntity> findByIdAndScheduleId(Integer id, Integer scheduleId);

    List<VersionEntity> findByScheduleId(Integer scheduleId);

    Page<VersionEntity> findAllByScheduleId(Pageable pageable, Integer scheduleId);

}
