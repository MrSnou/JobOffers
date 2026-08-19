package com.joboffersapi.infrastructure.security;

import com.joboffersapi.infrastructure.usercrud.dto.JwtResponseDto;
import com.joboffersapi.infrastructure.usercrud.dto.LoginRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;

    public JwtResponseDto authenticateAndGenerateToken(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));
        return JwtResponseDto.builder().build();
    }



}
