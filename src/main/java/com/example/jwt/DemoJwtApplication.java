package com.example.jwt;

import com.example.jwt.dao.DemoUsersRepository;
import com.example.jwt.dto.DemoUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJwtApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(DemoUsersRepository repository, PasswordEncoder passwordEncoder) {
        return args ->
            repository.findDemoUserByUsername("scrynkaa@gmail.com").orElseGet(() ->
                repository.save(new DemoUser("scrynkaa@gmail.com",
                        passwordEncoder.encode("password"),
                        new String[] {"ROLE_ADMIN", "ROLE_USER"})));

    }
}
