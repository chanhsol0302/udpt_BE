package com.example.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.LoginResponse;
import com.example.authservice.dto.UserAuthResponse;
import com.example.authservice.model.Session;
import com.example.authservice.repository.SessionRepository;
import com.example.authservice.security.JwtUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset; // Để chuyển LocalDateTime sang epoch milliseconds
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SessionRepository sessionRepo;

 // KHAI BÁO VÀ INJECT RestTemplate
    private final RestTemplate restTemplate;

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LoginResponse authenticate(LoginRequest request) {
        UUID userId;
        String role;
        String username;

        String userServiceAuthUrl = "http://localhost:8082/users/authenticate";

        try {
            UserAuthResponse userAuthResponse = restTemplate.postForObject(
                userServiceAuthUrl,
                request,
                UserAuthResponse.class
            );

            if (userAuthResponse == null || userAuthResponse.getId() == null) {
                throw new RuntimeException("Invalid credentials or user not found.");
            }

            userId = userAuthResponse.getId();
            role = userAuthResponse.getRole();
            username = userAuthResponse.getEmail();

        } catch (Exception e) {
            System.err.println("Authentication call to User Service failed: " + e.getMessage());
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }

        // Tạo JWT Token
        String token = jwtUtil.generateToken(username, userId, role);

        // Lưu Session vào Database
        Session session = new Session();
        session.setId(UUID.randomUUID());
        session.setUserId(userId);
        session.setToken(token);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(60); // 1 giờ
        session.setExpiresAt(expiresAt);
        sessionRepo.save(session);

        // Chuẩn bị LoginResponse để gửi về client
        LoginResponse response = new LoginResponse();
        response.setUserId(userId);
        response.setToken(token);
        response.setExpiresAt(expiresAt.toInstant(ZoneOffset.UTC).toEpochMilli());
        response.setUsername(username);
        response.setRole(role);

        return response;
    }
}