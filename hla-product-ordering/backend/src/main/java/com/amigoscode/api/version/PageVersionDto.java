package com.amigoscode.api.version;

import java.util.List;

public record PageVersionDto(
    List<VersionDto> versions,
    Integer currentPage,
    Integer totalPages,
    Long totalElements
)
{}
