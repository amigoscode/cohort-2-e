package com.amigoscode.api.auth;

import com.amigoscode.api.user.UserDto;
import com.amigoscode.api.user.UserDtoMapper;
import com.amigoscode.security.JWTUtil;
import com.amigoscode.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDtoMapper userDtoMapper;
    private final JWTUtil jwtUtil;

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = userDtoMapper.toDto(principal.getUser());
        String token = jwtUtil.issueToken(userDto.email(), userDto.role());
        return new AuthenticationResponse(token, userDto);
    }

}
