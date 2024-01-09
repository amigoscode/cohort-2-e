package com.amigoscode.domain.provider;

import com.amigoscode.domain.email.PageEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class ProviderService{
    private final ProviderRepository providerRepository;
    private final Clock clock;


    public Provider save(Provider provider, Integer userId) {
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        provider.setCreatedAt(createdAt);
        provider.setCreatedBy(userId);
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

    public PageProvider findAllByName(String providerName,Pageable pageable) {
        return providerRepository.findAllByName(providerName,pageable);
    }

    public Provider getPreferredProvider() {
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        PageProvider pageProvider = providerRepository.findAllByName("",pageable);
        if (pageProvider.getProviders().isEmpty()) {
            throw new ProviderNotFoundException();
        }
        return pageProvider.getProviders().get(0);
    }

}
