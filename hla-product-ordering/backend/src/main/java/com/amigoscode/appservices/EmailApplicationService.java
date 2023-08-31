package com.amigoscode.appservices;

import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.email.EmailNotFoundException;
import com.amigoscode.domain.email.EmailService;
import com.amigoscode.domain.email.PageEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailApplicationService {
    private final EmailService emailService;

    public Email findById(Integer id){
        return emailService.findById(id);
    }

    public PageEmail findAll(Pageable pageable) { return emailService.findAll(pageable); }

    public PageEmail findUnsent(Pageable pageable) { return emailService.findUnsent(pageable); }

    @Transactional
    public Email save(Email email) {
        return emailService.save(email);
    }

    public void send(Email email) {
        emailService.send(email);
    }


}
