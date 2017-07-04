package com.example.jwt.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class LoginResponse {
    @NonNull
    private String token;
}
