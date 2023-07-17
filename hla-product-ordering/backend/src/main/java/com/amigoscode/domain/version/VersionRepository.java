package com.amigoscode.domain.version;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VersionRepository {
    Optional<Version> findById(Integer id, Integer scheduleId);

    PageVersion findAll(Pageable pageable, Integer scheduleId);

    Version save(Version version);

    void update(Version version);

    void removeById(Integer id);


}
