package com.amigoscode.domain.patient;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PagePatient implements Serializable {

    List<Patient> patients;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;

}
