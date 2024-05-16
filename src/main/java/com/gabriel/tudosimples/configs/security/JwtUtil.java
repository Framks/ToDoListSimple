package com.gabriel.tudosimples.configs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "614E645267556B58703273357638782F413F4428472B4B6250655368566D5971";

    @Value("${jwt.secret}")
    private String seccretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String createToken(UserDetails userDetails){
        final var now = LocalDateTime.now();
        return Jwts.builder()
                .claims(new HashMap<>())
                .subject(userDetails.getUsername())
                .issuedAt(convertFromLocalDateTime(now))
                .expiration(convertFromLocalDateTime(now.plusHours(1L)))
                .signWith(this.getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(this.seccretKey.getBytes()));
    }

    private static Date convertFromLocalDateTime(LocalDateTime now) {
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }


    public String extractUsername(String jwt) {
        return this.extractClaims(jwt,Claims::getSubject);
    }

    private <T> T extractClaims(String jwt, Function<Claims, T> resolver) {
        final var claims = this.extractAllClaims(jwt);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(this.getSignKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }


    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final var username = this.extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return this.extratExpiration(jwt).before(new Date(System.currentTimeMillis()));
    }

    private Date extratExpiration(String jwt) {
        return this.extractClaims(jwt, Claims::getExpiration);
    }
}
