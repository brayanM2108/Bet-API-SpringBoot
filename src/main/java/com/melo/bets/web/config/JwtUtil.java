package com.melo.bets.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    @Value("${application.security.jwt.secret-key}")
    private String secret;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String createToken(String username) {
        return createToken(username, null);
    }

    public String createToken(String email, UUID userId) {
        return JWT.create()
                .withSubject(email)
                .withIssuer("melo-bets")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
                .withClaim("userId", userId.toString())
                .sign(algorithm);
    }


    public boolean isValid(String jwt) {
        try{
            JWT.require(algorithm).build().verify(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String jwt) {
        return JWT.require(algorithm).build().verify(jwt).getSubject();
    }

    public UUID getUserId(String jwt) {
        var claim = JWT.require(algorithm).build().verify(jwt).getClaim("userId");
        if (claim == null || claim.isNull()) {
            return null;
        }
        String claimValue = claim.asString();
        if (claimValue == null || claimValue.isBlank()) {
            return null;
        }
        return UUID.fromString(claimValue);
    }
}
