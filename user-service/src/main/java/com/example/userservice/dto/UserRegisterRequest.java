package com.example.userservice.dto;

import com.example.userservice.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate; // Dùng cho trường hợp ngày sinh ở tương lai
import jakarta.validation.constraints.PastOrPresent;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private UserRole role;
    
    // Bổ sung ngày 5/6 "Bệnh nhân tạo user -> lưu thông tin xuống patient"
    @NotBlank(message = "Name is required for patient")
    private String name;
    
    @NotNull(message = "Date of birth is required for patient")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;
    
    private String gender;
    private String address;
    
    // Sửa lại phần data cho phù hợp (not null)
    @NotNull(message = "Phone is required for patient")
    private String phone;
}