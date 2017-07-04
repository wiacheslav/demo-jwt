package com.example.jwt.service;

import com.example.jwt.dao.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String secretKey;
    @Value("${demo-jwt.ttl:3600}")
    private long ttl;
    @Autowired
    private TokenRepository tokenRepository;

    public TokenService(@Value("${demo-jwt.jwt-secret-key:secret}") String secretKey) {
        this.secretKey = Base64Utils.encodeToString(secretKey.getBytes());
    }

    public String addAuthentication(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Claims claims = Jwts.claims();
        claims.setSubject(user.getUsername());
        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(ttl)));
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, this.secretKey).compact();
    }

    public Authentication getAuthentication(String token) {
        if (tokenRepository.ifPresent(token)) {
            throw new RuntimeException("Token is blocked");
        }
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        List<String> roles = (List<String>) claims.get("roles");
        return new UsernamePasswordAuthenticationToken(username, null,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    public void logout(String token) {
        tokenRepository.blockToken(token, getTtl(token));
    }

    private long getTtl(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Instant expiration = claims.getExpiration().toInstant();
        Instant now = Instant.now();
        return Duration.between(now, expiration).getSeconds();
    }
}
