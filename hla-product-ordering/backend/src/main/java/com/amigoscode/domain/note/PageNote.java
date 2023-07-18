package com.amigoscode.domain.note;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageNote implements Serializable {

    List<Note> notes;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
