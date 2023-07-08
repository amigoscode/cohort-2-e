package com.amigoscode.domain.provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProviderServiceTest {
    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private ProviderService providerService;

    private final Provider fakeProvider = new Provider(
            1,
            "Cody",
            "cody@email.com",
            ZonedDateTime.of(2023, 6, 17, 12, 30, 20, 0, ZoneId.of("UTC")),
            5
    );

    private static ZonedDateTime NOW = ZonedDateTime.of(
            2023,
            6,
            17,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );


    @Test
    void update_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> providerService.update(fakeProvider));
    }
}