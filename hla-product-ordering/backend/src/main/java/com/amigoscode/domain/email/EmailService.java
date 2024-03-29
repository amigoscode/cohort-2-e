package com.amigoscode.domain.email;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;
    private final EmailSender emailSender;
    private final Clock clock;


    public Email findById(Integer id){
        return emailRepository.findById(id).
                orElseThrow(EmailNotFoundException::new);
    }

    public PageEmail findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

    public PageEmail findUnsent(Pageable pageable) {
        return emailRepository.findUnsent(pageable);
    }

    public Email save(Email email, Integer userId) {
        ZonedDateTime createdAt = ZonedDateTime.now(clock);
        email.setCreatedAt(createdAt);
        email.setUserId(userId);
        return emailRepository.save(email);
    }

    public void send(Email email) {
        emailSender.send(email);
        email.setSentAt(ZonedDateTime.now());
        emailRepository.save(email);

    }

}
