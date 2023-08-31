package com.amigoscode.api.provider;

import java.time.ZonedDateTime;

public record ProviderDto(
        Integer id,
        String email,
        String name
) {}
