package com.amigoscode.domain.provider;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProviderRepository {

    Provider save(Provider provider);

    void update(Provider provider);

    void remove(Integer id);

    Optional<Provider> findById(Integer id);

    PageProvider findAllByName(String providerName, Pageable pageable);
}
