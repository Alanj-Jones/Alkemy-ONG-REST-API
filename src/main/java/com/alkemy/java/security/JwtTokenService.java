package com.alkemy.java.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenService {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public static final String ROLES = "ROLES";

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token, String token2) {
        return getClaimFromToken(token, claims -> (List<String>) claims.get(ROLES));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        final Map<String, Object> claims = new HashMap<>();
        final UserDetails user = (UserDetails) authentication.getPrincipal();

        final List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put(ROLES, roles);
        return generateToken(claims, user.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        final long now = System.currentTimeMillis();
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(now))
                .setExpiration(new Date(now + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return username != null && !isTokenExpired(token);
    }
}