package com.example.authservice.dto;

import java.util.Objects;
import java.util.UUID;

public class LoginResponse {
    private String token;
    private long expiresAt;
    private String username;
    private String role;
    private UUID userId;

    public LoginResponse() {
    }

    public LoginResponse(String token, long expiresAt, String username, String role, UUID userId) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.username = username;
        this.role = role;
        this.userId = userId;
    }

    // --- Getters và Setters ---

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public UUID getUserId() {
    	return userId;
    }
    
    public void setUserId(UUID userId) {
    	this.userId = userId;
    }

    // --- equals() và hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse that = (LoginResponse) o;
        return expiresAt == that.expiresAt &&
               Objects.equals(token, that.token) &&
               Objects.equals(username, that.username) &&
               Objects.equals(role, that.role) &&
               Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, expiresAt, username, role, userId);
    }

    // --- toString() ---
    @Override
    public String toString() {
        return "LoginResponse{" +
               "token='" + (token != null ? token.substring(0, Math.min(token.length(), 20)) + "..." : "null") + '\'' + // Tránh in token quá dài
               ", expiresAt=" + expiresAt +
               ", username='" + username + '\'' +
               ", role='" + role + '\'' +
               ", userId=" + userId +
               '}';
    }
}