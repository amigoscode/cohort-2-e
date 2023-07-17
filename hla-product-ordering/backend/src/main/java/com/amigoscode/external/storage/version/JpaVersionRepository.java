package com.amigoscode.external.storage.version;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaVersionRepository extends JpaRepository<VersionEntity,Integer> {

    Optional<VersionEntity> findById(Integer id, Integer scheduleId);

    Page<VersionEntity> findAll(Pageable pageable, Integer scheduleId);

}
