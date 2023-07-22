package com.amigoscode.domain.email;

import com.amigoscode.domain.email.*;
import com.amigoscode.domain.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private EmailService emailService;

    private static ZonedDateTime createdAt = ZonedDateTime.of(
            2023,
            6,
            17,
            12,
            30,
            20,
            0,
            ZoneId.of("UTC")
    );

    private final Email fakeEmail = new Email(
            1,
            3,
            createdAt,
            4,
            "sample content",
            List.of()
    );




    @Test
    void find_by_id_method_should_return_founded_email_when_email_exist() {
        Mockito.when(orderService.findByEmailId(fakeEmail.getId())).thenReturn(List.of());
        Mockito.when(emailRepository.findById(fakeEmail.getId())).thenReturn(Optional.of(fakeEmail));

        //when
        Email foundedEmail = emailService.findById(fakeEmail.getId());

        //then
        Assertions.assertNotNull(foundedEmail);
        Assertions.assertEquals(fakeEmail.getId(), foundedEmail.getId());
        Assertions.assertEquals(fakeEmail.getId(), foundedEmail.getId());
        Assertions.assertEquals(fakeEmail.getId(), foundedEmail.getId());
    }

    @Test
    void find_by_id_method_should_throw_email_not_found_exception_when_email_does_not_exist() {
        Mockito.when(emailRepository.findById(fakeEmail.getId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(EmailNotFoundException.class,
                ()-> emailService.findById(fakeEmail.getId()));
    }

    @Test
    void save_method_should_return_saved_user_when_email_does_not_exist() {
        Mockito.when(emailRepository.save(Mockito.any(Email.class))).thenReturn(fakeEmail);

        //when
        Email savedEmail = emailService.save(fakeEmail);

        //then
        Assertions.assertNotNull(savedEmail);
        Assertions.assertEquals(fakeEmail.getId(), savedEmail.getId());
        Assertions.assertEquals(fakeEmail.getUserId(), savedEmail.getUserId());
        Assertions.assertEquals(fakeEmail.getProviderId(), savedEmail.getProviderId());
        Assertions.assertEquals(fakeEmail.getCreatedAt(), savedEmail.getCreatedAt());
        Assertions.assertEquals(fakeEmail.getContent(), savedEmail.getContent());
    }

    @Test
    void save_method_should_throw_user_already_exist_exception_when_email_exist() {
        Mockito.when(emailRepository.save(Mockito.any(Email.class)))
                .thenThrow(new EmailAlreadyExistsException());

        //then
        Assertions.assertThrows(EmailAlreadyExistsException.class,
                ()-> emailService.save(fakeEmail));
    }

}