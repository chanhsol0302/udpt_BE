package com.example.userservice.dto;

import com.example.userservice.model.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}