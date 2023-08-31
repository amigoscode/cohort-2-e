package com.amigoscode.appservices;

import com.amigoscode.BaseIT;
import com.amigoscode.TestEmailFactory;
import com.amigoscode.TestProviderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailApplicationServiceIT extends BaseIT {

    @MockBean
    JavaMailSender mailSender;

    @MockBean
    IAuthenticationFacade authenticationFacade;

    @Autowired
    EmailApplicationService emailApplicationService;

    @Autowired
    UserService userService;

    @Autowired
    ProviderApplicationService providerService;

    @Test
    void should_send_email() {
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        provider.setEmail("attwosix@gmail.com");
        Mockito.when(authenticationFacade.getLoggedInUserId()).thenReturn(savedUser.getId());
        Provider savedProvider = providerService.save(provider);

        Email email = TestEmailFactory.create();
        email.setUserId(savedUser.getId());
        email.setProviderId(savedProvider.getId());
        email.setContent("Email content.");
        Email savedEmail = emailApplicationService.save(email);

        //when
        emailApplicationService.send(savedEmail);
        Email sentEmail = emailApplicationService.findById(savedEmail.getId());


        //then
        Mockito.verify(mailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
        Assertions.assertNotNull(savedEmail.getId());
        Assertions.assertNotNull(sentEmail.getSentAt());
    }


}