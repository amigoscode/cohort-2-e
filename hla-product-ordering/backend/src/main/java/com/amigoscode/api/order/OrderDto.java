package com.amigoscode.api.order;

import java.time.ZonedDateTime;

public record OrderDto (
    Integer id,
    Integer scheduleId,
    Integer scheduleVersion,
    ZonedDateTime scheduledFor,
    Integer emailId,
    ZonedDateTime received
) {}
