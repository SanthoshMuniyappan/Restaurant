package com.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ors.common.model.Employee;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String generateToken(String name) {
        final Employee employee = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/v1/employee/name/{name}", name)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();

        if (employee == null) {
            throw new UsernameNotFoundException("User not found: " + name);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", employee.getRole());
        claims.put("id", employee.getId());
        return createToken(claims, employee.getName());
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
