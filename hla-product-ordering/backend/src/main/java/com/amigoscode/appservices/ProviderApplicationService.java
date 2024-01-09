package com.amigoscode.appservices;

import com.amigoscode.domain.provider.PageProvider;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderAlreadyExistsException;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.external.storage.provider.ProviderEntity;
import lombok.extern.java.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log
public class ProviderApplicationService {

    private final ProviderService providerService;
    private final IAuthenticationFacade authenticationFacade;

    @Transactional
    public Provider findById(Integer id) {
        return providerService.findById(id);
    }

    @Transactional
    public PageProvider findAllByName(String providerName,Pageable pageable) {
        return providerService.findAllByName(providerName,pageable);
    }

    @Transactional
    public Provider saveTransaction(Provider providerToSave) {
        return providerService.save(providerToSave, authenticationFacade.getLoggedInUserId());

    }

    public Provider save(Provider providerToSave) {
        try {
            return saveTransaction(providerToSave);
        } catch (DataIntegrityViolationException ex) {
            log.warning("Provider  " + providerToSave.getEmail() + " already exits in db");
            throw new ProviderAlreadyExistsException();
        }
    }
    @Transactional
    public void updateTransaction(Provider provider) {
        providerService.update(provider);
    }
    public void update(Provider providerToUpdate) {
            updateTransaction(providerToUpdate);

    }
    @Transactional
    public void removeById(Integer id) {
        providerService.removeById(id);
    }
}

