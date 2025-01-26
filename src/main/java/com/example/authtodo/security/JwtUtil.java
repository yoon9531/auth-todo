package com.example.authtodo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final Key key;
    private final Long accessTokenValidity;
    private final Long refreshTokenValidity;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey,
                   @Value("${jwt.access-token-validity}") String accessTokenValidity,
                   @Value("${jwt.refresh-token-validity}") String refreshTokenValidity) {
        String base64Secret = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenValidity = Long.parseLong(accessTokenValidity);
        this.refreshTokenValidity = Long.parseLong(refreshTokenValidity);
    }

    public String generateToken(String username, Long validity) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", "ROLE_USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(String username) {
        return generateToken(username, accessTokenValidity);
    }

    public String createRefreshToken(String username) {
        return generateToken(username, refreshTokenValidity);
    }


    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {

            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            log.info("subject: {}", subject);

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String validateTokenAndGetUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
