package com.amigoscode.domain.version;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageVersion implements Serializable {
    List<Version> versions;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
