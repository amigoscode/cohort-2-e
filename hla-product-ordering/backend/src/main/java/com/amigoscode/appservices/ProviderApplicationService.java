package com.amigoscode.appservices;

import com.amigoscode.domain.provider.PageProvider;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.security.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProviderApplicationService {

    private final ProviderService providerService;
    private final IAuthenticationFacade authenticationFacade;

    @Transactional
    public Provider findById(Integer id) {
        return providerService.findById(id);
    }

    @Transactional
    public PageProvider findAll(Pageable pageable) {
        return providerService.findAll(pageable);
    }

    @Transactional
    public Provider save(Provider providerToSave) {
        return providerService.save(providerToSave, authenticationFacade.getLoggedInUserId());
    }
    @Transactional
    public void update(Provider provider) {
        providerService.update(provider);
    }

    @Transactional
    public void removeById(Integer id) {
        providerService.removeById(id);
    }
}

