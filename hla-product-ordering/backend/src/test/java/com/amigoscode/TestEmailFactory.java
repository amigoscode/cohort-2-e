package com.amigoscode;

import com.amigoscode.domain.email.Email;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class TestEmailFactory {
    private static int emailSequence = 0;
    public static Email create() {
        emailSequence++;
        return new Email(
                null,
                emailSequence,
                ZonedDateTime.of(2023, 6, 17, 12, 40, 00, 0, ZoneId.of("UTC")),
                null,
                null,
                emailSequence,
                "Content" + emailSequence
        );
    }
}
