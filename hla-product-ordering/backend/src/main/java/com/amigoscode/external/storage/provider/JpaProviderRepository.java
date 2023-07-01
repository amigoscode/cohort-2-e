package com.amigoscode.external.storage.provider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProviderRepository extends JpaRepository<ProviderEntity, Integer> {
}
