package com.amigoscode.external.storage.schedule;

public enum Status {
    REVIEW("TO REVIEW BY TECHNOLOGIST"),
    REVIEWED("REVIEWED BY TECHNOLOGIST"),
    APPROVED_AND_EMAIL_SENT("APPROVED AND EMAIL SENT");

    Status(String s) {
    }
}

