package com.example.jwt.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@RequiredArgsConstructor
@Document(collection = "demoUsers")
public class DemoUser {
    @Id
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String[] roles;
}
