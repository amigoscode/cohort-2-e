package com.amigoscode;

import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserRole;
import com.amigoscode.domain.user.UserService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Log
public class DefaultUsers implements CommandLineRunner {

    private final UserService userService;

    public DefaultUsers(UserService userService) {
        this.userService = userService;
    }

    private final User adminUser = new User(
            null,
            "john@gmail.com",
            "John",
            "password",
            UserRole.ADMIN,
            ZonedDateTime.now()
    );

    private final User technologistUser = new User(
            null,
            "james@gmail.com",
            "James",
            "password",
            UserRole.TECHNOLOGIST,
            ZonedDateTime.now()
    );

    private final User medicalDoctorUser = new User(
            null,
            "mary@gmail.com",
            "Mary",
            "password",
            UserRole.MEDICAL_DOCTOR,
            ZonedDateTime.now()
    );

    @Override
    public void run(String... args) {
        try {
            addUser(adminUser);
            addUser(technologistUser);
            addUser(medicalDoctorUser);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void addUser(User user) {
        userService.save(user);
    }
}
