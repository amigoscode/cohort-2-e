package com.amigoscode.api.email;

import com.amigoscode.api.order.OrderDto;

import java.util.List;

public record PageEmailDto(
    List<EmailDto> emails,
    Integer currentPage,
    Integer totalPages,
    Long totalElements
)
{}
