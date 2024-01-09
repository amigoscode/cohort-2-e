package com.amigoscode.external.storage.provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProviderRepository extends JpaRepository<ProviderEntity, Integer> {
    Page<ProviderEntity> findAllByNameContaining(String providerName, Pageable pageable);
}
