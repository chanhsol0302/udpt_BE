package com.example.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretBase64;
    private Key signingKey;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    // Phương thức này sẽ được gọi sau khi các @Value đã được gán
    @PostConstruct
    public void init() {
        if (secretBase64 == null || secretBase64.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key (Base64 encoded) is not configured.");
        }
        // Giải mã Base64 và tạo Key object
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
    }

    public String generateToken(String username, UUID userId, String role) {
        return Jwts.builder()
            .setSubject(username)
            .claim("userId", userId.toString())
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(signingKey, SignatureAlgorithm.HS512) // Sử dụng Key object và phương thức signWith mới
            .compact();
    }

    public Claims parseToken(String token) throws Exception {
        if (signingKey == null) {
            throw new IllegalStateException("JWT signing key has not been initialized.");
        }
        try {
            return Jwts.parserBuilder() // Sử dụng parserBuilder()
                .setSigningKey(signingKey) // Sử dụng Key object
                .build() // Xây dựng parser
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            System.err.println("Error parsing JWT token: " + e.getMessage());
            throw new Exception("Invalid JWT token", e);
        }
    }
}