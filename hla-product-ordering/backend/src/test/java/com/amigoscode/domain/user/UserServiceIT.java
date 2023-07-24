package com.amigoscode.domain.user;

import com.amigoscode.BaseIT;
import com.amigoscode.TestUserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceIT extends BaseIT {

    @Autowired
    UserService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void add_user_test() {
        //given
        User user = TestUserFactory.createTechnologist();
        service.save(user);

        //when
        User readUser = service.findByEmail(user.getEmail());

        //then
        Assertions.assertEquals(user.getEmail(), readUser.getEmail());
        Assertions.assertEquals(user.getName(), readUser.getName());
        Assertions.assertTrue(passwordEncoder.matches(user.getPassword(), readUser.getPassword()));
    }

    @Test
    void get_by_email_should_return_correct_user() {
        //given
        User user1 = TestUserFactory.createTechnologist();
        User user2 = TestUserFactory.createMedicalDoctor();
        User user3 = TestUserFactory.createAdmin();
        service.save(user1);
        service.save(user2);
        service.save(user3);

        //when
        User readUser = service.findByEmail(user2.getEmail());

        //then
        Assertions.assertEquals(user2.getEmail(), readUser.getEmail());
        Assertions.assertEquals(user2.getName(), readUser.getName());
        Assertions.assertTrue(passwordEncoder.matches(user2.getPassword(), readUser.getPassword()));
    }
}