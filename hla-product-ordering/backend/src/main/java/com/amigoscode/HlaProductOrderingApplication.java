package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HlaProductOrderingApplication {

    public static void main (String[] args) {
        SpringApplication.run(
                HlaProductOrderingApplication.class,
                args);
    }

}
