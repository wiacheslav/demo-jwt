package com.example.jwt.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    public String toString() {
        return "com.example.jwt.dto.LoginRequest(username=" + this.getUsername() + ", password=[PROTECTED])";
    }
}
