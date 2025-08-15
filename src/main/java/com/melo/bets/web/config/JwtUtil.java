package com.melo.bets.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
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

        return JWT.create().
                withSubject(username).
                withIssuer("melo-bets").
                withIssuedAt(new Date()).
                withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(180))).
                sign(algorithm);
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
}
