package com.eissa.backend.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static Key SECRET_KEY;

    private static final int JWT_EXPIERY = 604800000; // 1 week

    @PostConstruct
    public void init() {
        if (secretKey != null && !secretKey.isEmpty()) {
            SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
        } else {
            throw new IllegalStateException("JWT secret key must not be null or empty");
        }
    }

    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIERY))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {

        Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
