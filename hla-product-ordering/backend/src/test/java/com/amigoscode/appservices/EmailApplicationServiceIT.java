package com.amigoscode.appservices;

import com.amigoscode.BaseIT;
import com.amigoscode.TestEmailFactory;
import com.amigoscode.TestOrderFactory;
import com.amigoscode.TestProviderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.email.Email;
import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderService;
import com.amigoscode.domain.provider.Provider;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

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

    @Autowired
    OrderService orderService;

    @Test
    void should_send_email() {
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);
        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        provider.setEmail("attwosix@gmail.com");
        Mockito.when(authenticationFacade.getLoggedInUserId()).thenReturn(savedUser.getId());
        Provider savedProvider = providerService.save(provider);
        Order order1 = orderService.save(TestOrderFactory.create());
        Order order2 = orderService.save(TestOrderFactory.create());
        Order order3 = orderService.save(TestOrderFactory.create());
        Order order4 = TestOrderFactory.create();
        order4.setEmailId(1);
        Order savedOrder4 = orderService.save(order4);

        Email email = TestEmailFactory.create();
        email.setUserId(savedUser.getId());
        email.setProviderId(savedProvider.getId());
        email.setOrders(List.of(order1.getId(), order2.getId(), order3.getId(), savedOrder4.getId()));

        //when
        Email sentEmail = emailApplicationService.send(email);
        Email savedEmail = emailApplicationService.save(sentEmail);

        //then
        Mockito.verify(mailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
        Assertions.assertNotNull(savedEmail.getId());
        Assertions.assertEquals(savedEmail.getOrders(), List.of(
                order1.getId(), order2.getId(), order3.getId()
        ));
    }


}