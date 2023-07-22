package com.amigoscode.api.email;

import java.time.ZonedDateTime;
import java.util.List;

public record EmailDto(
    Integer id,
    Integer providerId,
    ZonedDateTime createdAt,
    Integer userId,
    String content,
    List<Integer> orders
) {}
