package com.wms.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey123";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 🚀 Generate Token (NO EXPIRY)
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.toUpperCase())
                .setIssuedAt(new Date())
                // ❌ NO expiration added
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 👇 extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 👇 extract role
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // 🔍 parse token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}