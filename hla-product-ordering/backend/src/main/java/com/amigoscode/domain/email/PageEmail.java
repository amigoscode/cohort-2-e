package com.amigoscode.domain.email;


import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageEmail implements Serializable {

    List<Email> emails;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
