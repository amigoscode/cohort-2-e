package com.amigoscode.api.order;

import java.util.List;

public record PageOrderDto(
    List<OrderDto> orders,
    Integer currentPage,
    Integer totalPage,
    Long totalElements
)
{}
