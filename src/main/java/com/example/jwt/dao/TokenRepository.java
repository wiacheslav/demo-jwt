package com.example.jwt.dao;

import com.example.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void blockToken(String token, long ttl) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(token, String.valueOf(ttl), ttl, TimeUnit.SECONDS);
    }

    public boolean ifPresent(String token) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(token)).isPresent();
    }
}
