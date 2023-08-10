package com.amigoscode.appservices;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    Integer getLoggedInUserId();
}
