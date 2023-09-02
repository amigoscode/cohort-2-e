package com.amigoscode.domain.email;

import com.amigoscode.BaseIT;
import com.amigoscode.TestEmailFactory;
import com.amigoscode.TestProviderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.appservices.IAuthenticationFacade;
import com.amigoscode.appservices.ProviderApplicationService;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EmailServiceIT extends BaseIT {

    @MockBean
    IAuthenticationFacade authenticationFacade;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    ProviderApplicationService providerService;

    @Test
    void get_by_id_should_return_correct_email() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);

        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());

        Mockito.when(authenticationFacade.getLoggedInUserId()).thenReturn(savedUser.getId());
        Provider savedProvider = providerService.save(provider);

        Email email1 = TestEmailFactory.create();
        Email email2 = TestEmailFactory.create();
        Email email3 = TestEmailFactory.create();

        email1.setUserId(savedUser.getId());
        email2.setUserId(savedUser.getId());
        email3.setUserId(savedUser.getId());

        email1.setProviderId(savedProvider.getId());
        email2.setProviderId(savedProvider.getId());
        email3.setProviderId(savedProvider.getId());


        Email savedEmail1 = emailService.save(email1, email1.getUserId());
        Email savedEmail2 = emailService.save(email2, email2.getUserId());
        Email savedEmail3 = emailService.save(email3, email3.getUserId());

        //when
        Email readEmail = emailService.findById(savedEmail2.getId());

        //then
        Assertions.assertEquals(savedEmail2.getId(), readEmail.getId());
        Assertions.assertEquals(savedEmail2.getUserId(), readEmail.getUserId());
        Assertions.assertEquals(savedEmail2.getProviderId(), readEmail.getProviderId());
        Assertions.assertEquals(savedEmail2.getCreatedAt().toInstant().atZone(ZoneOffset.UTC), readEmail.getCreatedAt().toInstant().atZone(ZoneOffset.UTC));
        Assertions.assertEquals(savedEmail2.getContent(), readEmail.getContent());
    }

    @Test
    void find_unsent_should_return_unsent_emails() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);

        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());

        Mockito.when(authenticationFacade.getLoggedInUserId()).thenReturn(savedUser.getId());
        Provider savedProvider = providerService.save(provider);

        Email email1 = TestEmailFactory.create();
        Email email2 = TestEmailFactory.create();
        Email email3 = TestEmailFactory.create();
        Email email4 = TestEmailFactory.create();

        email1.setUserId(savedUser.getId());
        email2.setUserId(savedUser.getId());
        email3.setUserId(savedUser.getId());
        email4.setUserId(savedUser.getId());

        email1.setProviderId(savedProvider.getId());
        email2.setProviderId(savedProvider.getId());
        email3.setProviderId(savedProvider.getId());
        email4.setProviderId(savedProvider.getId());

        email1.setSentAt(ZonedDateTime.now());
        email2.setSentAt(null);
        email3.setSentAt(null);
        email4.setSentAt(ZonedDateTime.now());


        Email savedEmail1 = emailService.save(email1, email1.getUserId());
        Email savedEmail2 = emailService.save(email2, email2.getUserId());
        Email savedEmail3 = emailService.save(email3, email3.getUserId());
        Email savedEmail4 = emailService.save(email4, email4.getUserId());

        //when
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        PageEmail readEmails = emailService.findUnsent(pageable);

        //then
        Assertions.assertNotNull(readEmails);
        Assertions.assertEquals(2, readEmails.getEmails().size());
    }

}