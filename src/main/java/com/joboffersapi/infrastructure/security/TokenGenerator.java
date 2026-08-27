package com.joboffersapi.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Component
public class TokenGenerator {

    private final Clock clock;

    private final JwtConfigurationProperties jwtConfigurationProperties;


    public String generateToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfigurationProperties.secretKey());
        Instant issuedAt = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = issuedAt.plusSeconds(jwtConfigurationProperties.expirationTimeInSeconds());
        String issuer = jwtConfigurationProperties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);

    }
}
