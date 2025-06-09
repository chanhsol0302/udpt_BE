package com.example.userservice.dto;

import com.example.userservice.model.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Email(message = "Email should be valid")
    private String email;
    private String password;
    private UserRole role;
}