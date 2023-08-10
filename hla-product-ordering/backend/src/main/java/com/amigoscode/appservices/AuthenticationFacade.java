package com.amigoscode.appservices;

import com.amigoscode.api.user.UserDtoMapper;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import com.amigoscode.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade implements IAuthenticationFacade {
    private final UserService userService;
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Integer getLoggedInUserId() {
        Authentication authentication = getAuthentication();
        User user = userService.findByEmail(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        return user.getId();
    }

}