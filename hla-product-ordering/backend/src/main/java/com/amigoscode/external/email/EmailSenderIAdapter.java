package com.amigoscode.external.email;

import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.email.EmailSender;
import com.amigoscode.domain.email.EmailService;
import com.amigoscode.domain.provider.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmailSenderIAdapter implements EmailSender {

    private final JavaMailSender mailSender;
    private final ProviderService providerService;

    @Override
    public Email send(final Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(
                providerService.findById(email.getProviderId()).getEmail()
        );
        message.setSubject("New order");
        message.setText(email.getContent());
        mailSender.send(message);
        return email;
    }
}
