package com.amigoscode.external.scheduler;

import com.amigoscode.appservices.EmailApplicationService;
import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.email.PageEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
@Log
public class EmailScheduler {
    private final EmailApplicationService emailService;
    private static final AtomicReference<ZonedDateTime> lastMailSentAt = new AtomicReference<>();

    @Scheduled(fixedRate = 30_000)
    public void reportCurrentTime() {
        log.info("Heartbeat");

        ZonedDateTime currentTime = ZonedDateTime.now();
        if (lastMailSentAt.get() == null || currentTime.isAfter(lastMailSentAt.get().plusMinutes(2))) {
            int page = 0;
            int size = 3;
            Pageable pageable = PageRequest.of(page, size);
            PageEmail unsentPageEmails = emailService.findUnsent(pageable);
            List<Email> unsentEmails = unsentPageEmails.getEmails();
            if(!unsentEmails.isEmpty()) {
                log.info("Unsent emails: " + unsentEmails);
                Email email = unsentEmails.get(0);
                emailService.send(email);
                lastMailSentAt.set(currentTime);
                log.info("Sent email: " + email);
            }
        }
    }
}
