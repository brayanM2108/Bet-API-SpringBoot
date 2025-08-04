package com.melo.bets.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final String SECRET = "m3l0-b3ts";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    public String createToken(String username) {

        return JWT.create().
                withSubject(username).
                withIssuer("melo-bets").
                withIssuedAt(new Date()).
                withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60))).
                sign(ALGORITHM);
    }

    public boolean isValid(String jwt) {
        try{
            JWT.require(ALGORITHM).build().verify(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getSubject();
    }
}
