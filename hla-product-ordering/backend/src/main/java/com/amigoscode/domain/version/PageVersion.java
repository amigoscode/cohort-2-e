package com.amigoscode.domain.version;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public record PageVersion(List<Version> versions, Integer currentPage, Integer totalPages,
                          Long totalElements) implements Serializable {

}
