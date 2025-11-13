package com.bookreview.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {

    private final SecurityConstants securityConstants;

    public JWTGenerator(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + securityConstants.jwtExpiration);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(securityConstants.jwtSecretKey)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(securityConstants.jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(securityConstants.jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationServiceException("JWT is invalid or expired", e);
        }
    }
}
