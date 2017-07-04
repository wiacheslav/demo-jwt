package com.example.jwt.web.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0")
public class User {

    @RequestMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sayHello() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Hi, " + user + "!!!";
    }
}
