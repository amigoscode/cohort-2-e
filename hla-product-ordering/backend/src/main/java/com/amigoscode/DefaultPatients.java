package com.amigoscode;

import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Log
public class DefaultPatients implements CommandLineRunner {

    private final PatientService patientService;

    public DefaultPatients(ProviderService providerService) {
        this.patientService = providerService;
    }

    private final Provider provider1 = new Provider(
            null,
            "John",
            "provider.john@gmail.com",
            ZonedDateTime.now(),
            1
    );

    private final Provider provider2 = new Provider(
            null,
            "Sue",
            "provider.sue@gmail.com",
            ZonedDateTime.now(),
            1
    );

    private final Provider provider3 = new Provider(
            null,
            "Angela",
            "provider.angela@gmail.com",
            ZonedDateTime.now(),
            1
    );

    @Override
    public void run(String... args) {
        try {
            addProvider(provider1);
            addProvider(provider2);
            addProvider(provider3);

        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void addProvider(Provider provider) {
        patientService.save(provider);
    }
}
