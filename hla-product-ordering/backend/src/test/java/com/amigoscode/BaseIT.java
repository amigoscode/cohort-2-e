package com.amigoscode;

import com.amigoscode.domain.patient.PatientRepository;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserRole;
import com.amigoscode.domain.user.UserService;
import com.amigoscode.external.storage.email.JpaEmailRepository;
import com.amigoscode.external.storage.note.JpaNoteRepository;
import com.amigoscode.external.storage.order.JpaOrderRepository;
import com.amigoscode.external.storage.provider.JpaProviderRepository;
import com.amigoscode.external.storage.schedule.JpaScheduleRepository;
import com.amigoscode.external.storage.user.JpaUserRepository;
import com.amigoscode.external.storage.version.JpaVersionRepository;
import com.amigoscode.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ActiveProfiles("it")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = HlaProductOrderingApplication.class
)
@ExtendWith(SpringExtension.class)
public class BaseIT {

    @Autowired
    protected Environment environment;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected UserService userService;

    protected BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected JWTUtil jwtUtil;

    @Autowired
    private ServerPortService serverPortService;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JpaOrderRepository jpaOrderRepository;

    @Autowired
    private JpaProviderRepository jpaProviderRepository;
    @Autowired
    private JpaNoteRepository jpaNoteRepository;

    @Autowired
    private JpaEmailRepository jpaEmailRepository;

    @Autowired
    private JpaVersionRepository jpaVersionRepository;

    @Autowired
    private JpaScheduleRepository jpaScheduleRepository;
    @BeforeEach
    void init() {
        jpaEmailRepository.deleteAll();
        jpaOrderRepository.deleteAll();
        jpaNoteRepository.deleteAll();
        jpaVersionRepository.deleteAll();
        jpaScheduleRepository.deleteAll();
        jpaProviderRepository.deleteAll();
        jpaUserRepository.deleteAll();
        patientRepository.deleteAll();
        addTestUsers();
    }

    private User adminUser = new User(
            null,
            "admin@gmail.com",
            "John",
            "password",
            UserRole.ADMIN,
            ZonedDateTime.of(
                    2023,
                    6,
                    17,
                    12,
                    30,
                    20,
                    0,
                    ZoneId.of("UTC"))
    );

    private User technologistUser = new User(
            null,
            "technologist@gmail.com",
            "John",
            "password",
            UserRole.TECHNOLOGIST,
            ZonedDateTime.of(
                    2023,
                    6,
                    17,
                    12,
                    30,
                    20,
                    0,
                    ZoneId.of("UTC"))
    );

    private User mdUser = new User(
            null,
            "md@gmail.com",
            "John",
            "password",
            UserRole.MEDICAL_DOCTOR,
            ZonedDateTime.of(
                    2023,
                    6,
                    17,
                    12,
                    30,
                    20,
                    0,
                    ZoneId.of("UTC"))
    );

    protected String localUrl(String endpoint) {
        int port = serverPortService.getPort();
        return "http://localhost:" + port + endpoint;
    }

    protected void addTestUsers() {
        userService.save(adminUser);
        userService.save(technologistUser);
        userService.save(mdUser);
    }

    protected String getAccessTokenForUser(User user) {
        String token = jwtUtil.issueToken(user.getEmail(), "ROLE_" + user.getRole());
        return "Bearer " + token;
    }

    protected String getAccessTokenForAdmin() {
        String token = jwtUtil.issueToken(adminUser.getEmail(), "ROLE_" + adminUser.getRole());
        return "Bearer " + token;
    }

    protected String getAccessTokenForTechnologist() {
        String token = jwtUtil.issueToken(technologistUser.getEmail(), "ROLE_" + technologistUser.getRole());
        return "Bearer " + token;
    }

    protected String getAccessTokenForMD() {
        String token = jwtUtil.issueToken(mdUser.getEmail(), "ROLE_" + mdUser.getRole());
        return "Bearer " + token;
    }

    protected <T, U> ResponseEntity<U> callHttpMethod(
            HttpMethod httpMethod,
            String url,
            String accessToken,
            T body,
            Class<U> mapToObject
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        headers.add(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<T> requestEntity;
        if (body == null) {
            requestEntity = new HttpEntity<>(headers);
        } else {
            requestEntity = new HttpEntity<>(body, headers);
        }
        return restTemplate.exchange(
                localUrl(url),
                httpMethod,
                requestEntity,
                mapToObject
        );
    }

}
