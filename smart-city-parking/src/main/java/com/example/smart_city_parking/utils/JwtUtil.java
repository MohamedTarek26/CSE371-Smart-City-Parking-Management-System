package com.example.smart_city_parking.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "jDaiwzJY1YN7BFaO5yAiN9vXmoxBSjrxIFcmf3TJliQ=";


    // Modify the generateToken method to include additional data
    public String generateToken(String username, String email, String userName, int roleId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email); // Add email to the token
        claims.put("username", userName); // Add username to the token
        claims.put("roleId", roleId); // Add roleId to the token
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String extractEmail(String token) {
        return (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("email");
    }

    public String extractUserName(String token) {
        return (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("username");
    }

    public int extractRoleId(String token) {
        return (int) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("roleId");
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}