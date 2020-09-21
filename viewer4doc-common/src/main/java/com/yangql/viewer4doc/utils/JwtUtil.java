package com.yangql.viewer4doc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private Key key;
    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());

    }

    public String createToken(Long userId, String email) {
        JwtBuilder builder = Jwts.builder()
                .claim("userId",userId)
                .claim("email",email);

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
