package com.amigoscode.api.email;

import com.amigoscode.appservices.EmailApplicationService;
import com.amigoscode.domain.email.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/emails",
        produces = "application/json",
        consumes = "application/json"
)
@Log
class EmailController {
    private final EmailApplicationService emailService;

    private final EmailDtoMapper emailMapper;

    private final PageEmailDtoMapper pageEmailDtoMapper;

    @GetMapping( path = "/{emailId}")
    public ResponseEntity<EmailDto> getEmail(@PathVariable Integer emailId) {
        Email email = emailService.findById(emailId);
        return ResponseEntity
                .ok(emailMapper.toDto(email));
    }

    @GetMapping
    public ResponseEntity<PageEmailDto> getEmails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageEmailDto pageEmails = pageEmailDtoMapper.toPageDto(emailService.findAll(pageable));

        return ResponseEntity.ok(pageEmails);
    }

    @PostMapping
    public ResponseEntity<EmailDto> sendEmail(@RequestBody EmailDto dto){
        Email savedEmail = emailService.save(emailMapper.toDomain(dto));
        return ResponseEntity
                .ok(emailMapper.toDto(savedEmail));
    }
}
