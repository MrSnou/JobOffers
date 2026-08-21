package com.joboffersapi.infrastructure.security;

import com.joboffersapi.infrastructure.usercrud.dto.JwtResponseDto;
import com.joboffersapi.infrastructure.usercrud.dto.UserLoginRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;

    public JwtResponseDto authenticateAndGenerateToken(UserLoginRequestDto userLoginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.username(), userLoginRequestDto.password()));
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = tokenGenerator.generateToken(user);
        String username = user.getUsername();
        return JwtResponseDto.builder()
                .token(token)
                .username(username)
                .build();
    }



}
