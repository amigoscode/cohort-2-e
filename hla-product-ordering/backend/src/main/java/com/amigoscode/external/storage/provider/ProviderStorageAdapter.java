package com.amigoscode.external.storage.provider;

import com.amigoscode.domain.provider.PageProvider;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderAlreadyExistsException;
import com.amigoscode.domain.provider.ProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public
class ProviderStorageAdapter implements ProviderRepository {

    private final JpaProviderRepository providerRepository;
    private final ProviderEntityMapper mapper;

    @Override
    public Provider save(final Provider provider) {
        try {
            ProviderEntity saved = providerRepository.save(mapper.toEntity(provider));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            log.warning("User " + provider.getEmail() + " already exits in db");
            throw new ProviderAlreadyExistsException();
        }
    }

    @Override
    public void update(final Provider provider) {
        providerRepository.findById(provider.getId()).ifPresent(userEntity -> providerRepository.save(mapper.toEntity(provider)));
    }

    @Override
    public void remove(final Integer id) {
        providerRepository.findById(id).ifPresent(providerEntity -> providerRepository.deleteById(id));
    }

    @Override
    public Optional<Provider> findById(final Integer id) {
        return providerRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageProvider findAll(final Pageable pageable) {
        Page<ProviderEntity> pageOfProvidersEntity = providerRepository.findAll(pageable);
        List<Provider> providersOnCurrentPage = pageOfProvidersEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageProvider(
                providersOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfProvidersEntity.getTotalPages(),
                pageOfProvidersEntity.getTotalElements()
        );
    }
}
