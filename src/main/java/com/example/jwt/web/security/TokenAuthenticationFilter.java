package com.example.jwt.web.security;

import com.example.jwt.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenService tokenService;
    private AntPathRequestMatcher antPathRequestMatcher;

    public TokenAuthenticationFilter() {
        this.antPathRequestMatcher = new AntPathRequestMatcher("/v1.0/**");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.antPathRequestMatcher.matches(request)) {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
                response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"" + request.getRequestURI() + "\",error=\"invalid_request\"");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                SecurityContextHolder.clearContext();
            } else {
                try {
                    String token = authHeader.replace("Bearer ", "");
                    Authentication authentication = tokenService.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } catch (Exception ex) {
                    response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"" + request.getRequestURI() + "\",error=\"invalid_token\"");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    SecurityContextHolder.clearContext();
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
