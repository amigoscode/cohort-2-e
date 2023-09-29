package com.amigoscode.api.patient;

import com.amigoscode.domain.patient.Patient;
import com.amigoscode.domain.patient.PatientService;
import com.amigoscode.security.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/patients"
)
class PatientController {

    private final PatientService patientService;
    private final PatientDtoMapper patientMapper;
    private final PagePatientDtoMapper pagePatientDtoMapper;
    private final Security security;

    @GetMapping( path = "/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Integer id) {
        Patient patient = patientService.findById(id);
        return ResponseEntity
                .ok(patientMapper.toDto(patient));
    }

    @GetMapping
    public ResponseEntity<PagePatientDto> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagePatientDto pagePatients = pagePatientDtoMapper.toPageDto(patientService.findAll(pageable));

        return ResponseEntity.ok(pagePatients);
    }

}
