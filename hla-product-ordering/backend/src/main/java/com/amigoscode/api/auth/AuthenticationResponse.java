package com.amigoscode.api.auth;

import com.amigoscode.api.user.UserDto;

public record AuthenticationResponse(
        String token,
        UserDto userDto
) {
}
