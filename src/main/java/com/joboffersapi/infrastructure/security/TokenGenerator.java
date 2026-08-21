package com.joboffersapi.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${job_offers.security.secret-key}")
    private String SECRET_KEY;
    @Value("${job_offers.security.token-expiration-ms}")
    private Long expirationTime;


    public String generateToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        Instant issuedAt = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = issuedAt.plusSeconds(expirationTime);
        String issuer = "JobOffersAPI";
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);

    }
}
