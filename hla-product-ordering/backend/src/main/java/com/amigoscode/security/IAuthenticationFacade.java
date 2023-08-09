package com.amigoscode.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    Integer getLoggedInUserId();
}
