package com.example.jwt;

import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoJwtApplicationTests {

    @Test
    public void contextLoads() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzY3J5bmthYUBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImV4cCI6MTQ5OTE2NTkyNX0.Ze390z_SBjexHg4pvsmkjZOlyB4ven7ejhsN7G4ZFoKJa3GSUinWhMeTUG5Y5zxQZoui9cxONDT4WUNUPiiZBA";
        Jwts.parser().setSigningKey(Base64Utils.encodeToString("elvis_presley".getBytes())).parseClaimsJws(token).getBody().getSubject();
    }

}
