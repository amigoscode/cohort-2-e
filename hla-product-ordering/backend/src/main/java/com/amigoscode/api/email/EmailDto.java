package com.amigoscode.api.email;

import java.time.ZonedDateTime;
import java.util.List;

public record EmailDto(
    Integer id,
    Integer providerId,
    ZonedDateTime createdAt,
    ZonedDateTime sentAt,
    Integer scheduleId,
    Integer userId,
    String content
) {}
