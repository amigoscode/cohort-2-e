package com.amigoscode.api.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
