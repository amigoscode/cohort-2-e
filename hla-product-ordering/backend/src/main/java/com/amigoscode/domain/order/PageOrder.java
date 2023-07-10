package com.amigoscode.domain.order;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageOrder implements Serializable {

    List<Order> orders;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
