package com.example.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.LoginResponse;
import com.example.authservice.service.AuthService;

@RestController // Đánh dấu đây là một REST Controller
@RequestMapping("/auth") // Tất cả các API trong controller này sẽ bắt đầu bằng /auth
public class AuthController {

    @Autowired // Tiêm AuthService vào controller
    private AuthService authService;

    @PostMapping("/login") // Xử lý request POST tới /auth/login
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response); // Trả về 200 OK với LoginResponse
        } catch (RuntimeException e) {
            // Xử lý lỗi xác thực (ví dụ: thông tin đăng nhập không hợp lệ)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}