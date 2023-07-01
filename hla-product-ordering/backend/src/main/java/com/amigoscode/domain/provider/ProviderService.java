package com.amigoscode.domain.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final Clock clock;

    public Provider save(Provider provider) {
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        provider.setCreatedAt(createdAt);
        return providerRepository.save(provider);
    }

    public void update(Provider provider) {
        providerRepository.update(provider);
    }

    public void removeById(Integer id) {
        providerRepository.remove(id);
    }

    public Provider findById(Integer id) {
        return providerRepository.findById(id)
                .orElseThrow(ProviderNotFoundException::new);
    }

    public PageProvider findAll(Pageable pageable) {
        return providerRepository.findAll(pageable);
    }
}
