package com.amigoscode;


import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserRole;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestProviderFactory {

    private static int providerSequence = 10;

    public static Provider create() {
        providerSequence++;
        return new Provider(
                providerSequence,
                "Provider Name " + providerSequence,
                "newProvider" + providerSequence + "@example.com",
                ZonedDateTime.of(2023, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
                5
        );
    }

}
