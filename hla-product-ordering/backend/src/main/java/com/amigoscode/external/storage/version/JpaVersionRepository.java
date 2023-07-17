package com.amigoscode.external.storage.version;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVersionRepository extends JpaRepository<VersionEntity,Integer> {

}
