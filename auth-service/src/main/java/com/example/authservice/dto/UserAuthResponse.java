package com.example.authservice.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public class UserAuthResponse {
    private UUID id;
    private String email;
    private String role;
    // Có thể có thêm các trường khác nếu User Service trả về, ví dụ:
    private LocalDateTime createdAt;
    private	LocalDateTime updatedAt;
    // Constructor không đối số
    public UserAuthResponse() {
    }

    // Constructor với tất cả đối số
    public UserAuthResponse(UUID id, String email, String role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
    
    public LocalDateTime getCreatedAt() {
    	return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
    	return updatedAt;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
    	this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
    	this.updatedAt = updatedAt;
    }
}