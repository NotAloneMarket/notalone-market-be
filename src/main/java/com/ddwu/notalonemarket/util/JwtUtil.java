package com.ddwu.notalonemarket.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "notalone-secret-key-should-be-long-enough-12345";
    private final long expirationMs = 1000 * 60 * 60 * 3; // 3시간

    private final Key secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // Key 객체로 변환

    public String validateTokenAndGetLoginId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    
    public String generateToken(String loginId) {
        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256) // 🔥 순서 주의!
                .compact();
    }
}
