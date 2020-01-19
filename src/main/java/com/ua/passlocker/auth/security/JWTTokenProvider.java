package com.ua.passlocker.auth.security;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JWTTokenProvider {

    @Value("${auth.secret}")
    private String SECRET;

    public String doGenerateToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 60 * 1000))) // 1 hour
                .sign(HMAC512(SECRET.getBytes()));

    }
}
