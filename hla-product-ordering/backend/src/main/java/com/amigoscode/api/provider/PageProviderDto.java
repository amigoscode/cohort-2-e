package com.amigoscode.api.provider;

import java.util.List;

public record PageProviderDto(
        List<ProviderDto> providers,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {
}
