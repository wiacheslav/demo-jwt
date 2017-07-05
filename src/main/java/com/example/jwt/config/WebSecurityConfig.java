package com.example.jwt.config;

import com.example.jwt.dao.TokenRepository;
import com.example.jwt.service.TokenService;
import com.example.jwt.web.security.LogoutHandler;
import com.example.jwt.web.security.RestLoginFilter;
import com.example.jwt.web.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RestLoginFilter restLoginFilter;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private LogoutHandler logoutHandler;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // formatter:off
        http.csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers("/v1.0/login").permitAll()
                .antMatchers("/v1.0/**").authenticated()
                .and()
        .addFilterBefore(restLoginFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .logout().clearAuthentication(false)
                .invalidateHttpSession(false)
                .logoutUrl("/v1.0/auth/logout")
                .defaultLogoutSuccessHandlerFor(logoutHandler, new AntPathRequestMatcher("/v1.0/auth/logout", "POST"));
        /*.defaultLogoutSuccessHandlerFor((request, response, authentication) -> {
            System.out.println(authentication);
            String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
            System.out.println(tokenService.getTtl(token));
            tokenRepository.blockToken(token);
        }, new AntPathRequestMatcher("/auth/logout", "POST"));*/
        // formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
