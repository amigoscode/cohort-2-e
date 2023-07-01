package com.amigoscode.domain.user;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageUser implements Serializable {

    List<User> users;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}