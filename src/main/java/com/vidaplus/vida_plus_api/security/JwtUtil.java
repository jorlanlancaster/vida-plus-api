package com.vidaplus.vida_plus_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    // chave simples para o projeto acadêmico; em produção use .env/secret manager
    private static final Key KEY = Keys.hmacShaKeyFor("vida-plus-api-secret-key-32-bytes-minimo!!".getBytes());
    private static final long EXPIRATION_MS = 1000L * 60 * 60; // 1h

    public static String generateToken(String subject, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);
        return Jwts.builder()
                .setSubject(subject)       // email
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static io.jsonwebtoken.Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
