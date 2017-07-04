package com.example.jwt.service;

import com.example.jwt.dao.DemoUsersRepository;
import com.example.jwt.dto.DemoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DemoUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DemoUsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Load user by username: {}", username);
        DemoUser demoUser = repository.findDemoUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(demoUser.getUsername(), demoUser.getPassword(),
                AuthorityUtils.createAuthorityList(demoUser.getRoles()));
    }
}
