package com.alkemy.java.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final Optional<String> jwt = getJwtFromRequest(request);
        jwt.ifPresent(token -> {
            try {
                if (jwtTokenService.validateToken(token)) {
                    setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), token);
                }
            } catch (IllegalArgumentException | MalformedJwtException | ExpiredJwtException e) {
                logger.error("Unable to get JWT Token or JWT Token has expired");
            }
        });

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(WebAuthenticationDetails authDetails, String token) {
        final String username = jwtTokenService.getUsernameFromToken(token);
        final List<String> roles = jwtTokenService.getRoles(token, token);
        final UserDetails userDetails = new User(username, "",
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        authentication.setDetails(authDetails);
   
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

}