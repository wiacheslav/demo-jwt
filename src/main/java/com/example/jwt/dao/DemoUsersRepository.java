package com.example.jwt.dao;

import com.example.jwt.dto.DemoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemoUsersRepository extends MongoRepository<DemoUser, String> {

    Optional<DemoUser> findDemoUserByUsername(String username);
}
